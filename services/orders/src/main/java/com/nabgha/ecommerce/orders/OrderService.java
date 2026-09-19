package com.nabgha.ecommerce.orders;

import com.nabgha.ecommerce.customer.CustomerClient;
import com.nabgha.ecommerce.exception.BusinessException;
import com.nabgha.ecommerce.kafka.OrderConfirmation;
import com.nabgha.ecommerce.kafka.OrderProducer;
import com.nabgha.ecommerce.orderLines.OrderLineRequest;
import com.nabgha.ecommerce.orderLines.OrderLineService;
import com.nabgha.ecommerce.payment.PaymentClient;
import com.nabgha.ecommerce.payment.PaymentRequest;
import com.nabgha.ecommerce.product.ProductClient;
import com.nabgha.ecommerce.product.PurchaseRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderLineService orderLineService;

    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;

    @Transactional
    public Order createOrder(OrderRequest request) {

        // 1. check the customer    -> customer-microservice  (OpenFein)
        var customer = this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order because customer not found"));

        // 2. purchase the product  -> product-microservice (RestClient)
            var purchaseProducts = this.productClient.purchaseProducts(request.products());

        // 3. persist order && order lines
        var order = orderRepository.save(orderMapper.toOrder(request));

        for (PurchaseRequest purchaseRequest : request.products()) {
            orderLineService.saveOrderline(
                    new OrderLineRequest(
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }
        // 4. todo: start payment process (order microservice)
        var paymentRequest = new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );
        paymentClient.requestOrderPayment(paymentRequest);

        // 5. send order confirmation -> notification microservice (kafka)
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchaseProducts
                )
        );
        return order;
    }

    public List<OrderResponse> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toOrderResponse)
                .toList();
    }

    public OrderResponse findById(String id) {
        var order = orderRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Cannot find order"));
        return orderMapper.toOrderResponse(order);
    }
}
