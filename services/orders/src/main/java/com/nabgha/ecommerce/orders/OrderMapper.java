package com.nabgha.ecommerce.orders;

import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    Order toOrder(OrderRequest request) {
        return Order.builder()
                .reference(request.reference())
                .totalAmount(request.amount())
                .paymentMethod(request.paymentMethod())
                .customerId(request.customerId())
                //.orderLines(request.or)
                .build();
    }

    OrderResponse toOrderResponse(Order order) {
        return OrderResponse.builder()
                .orderId(order.getId())
                .amount(order.getTotalAmount())
                .reference(order.getReference())
                .paymentMethod(order.getPaymentMethod())
                .customerId(order.getCustomerId())
                .build();
    }
}
