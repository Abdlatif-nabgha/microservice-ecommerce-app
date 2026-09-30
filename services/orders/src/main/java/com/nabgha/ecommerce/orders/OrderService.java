package com.nabgha.ecommerce.orders;

import com.nabgha.ecommerce.customer.CustomerClient;
import com.nabgha.ecommerce.exception.CustomerNotFoundException;
import com.nabgha.ecommerce.exception.OrderNotFoundException;
import com.nabgha.ecommerce.kafka.OrderConfirmation;
import com.nabgha.ecommerce.kafka.OrderProducer;
import com.nabgha.ecommerce.orderLines.OrderLine;
import com.nabgha.ecommerce.payment.PaymentClient;
import com.nabgha.ecommerce.payment.PaymentRequest;
import com.nabgha.ecommerce.product.ProductClient;
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

    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;

    @Transactional
    public OrderResponse createOrder(OrderRequest request) {

        // 1. check the customer    -> customer-microservice  (OpenFein)
        var customer = this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new CustomerNotFoundException(request.customerId()));

        // 2. persist order and order lines before changing stock in the product service
        var order = orderMapper.toOrder(request);
        request.products().forEach(p ->
                order.addOrderLine(OrderLine.builder()
                        .productId(p.productId())
                        .quantity(p.quantity())
                        .build()
                )
        );
        var persistedOrder = orderRepository.saveAndFlush(order);
        var orderResponse = orderMapper.toOrderResponse(persistedOrder);

        // 3. purchase the product -> product-microservice (RestClient)
        var purchaseProducts = this.productClient.purchaseProducts(request.products());

        // 4. start payment process (payment microservice)
        var paymentRequest = new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                persistedOrder.getId(),
                persistedOrder.getReference(),
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
        return orderResponse;
    }

    public List<OrderResponse> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toOrderResponse)
                .toList();
    }

    public OrderResponse findById(String id) {
        var order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        return orderMapper.toOrderResponse(order);
    }
}
