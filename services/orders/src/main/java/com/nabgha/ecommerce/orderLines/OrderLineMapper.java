package com.nabgha.ecommerce.orderLines;

import com.nabgha.ecommerce.orders.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderLineMapper {

    OrderLine toOrderLine(OrderLineRequest order) {
        return OrderLine.builder()
                .productId(order.productId())
                .quantity(order.quantity())
                .order(Order.builder()
                        .id(order.orderId())
                        .build()
                )
                .build();

    }

    OrderLineResponse toOrderLineResponse(OrderLine orderLine) {
        return OrderLineResponse.builder()
                .id(orderLine.getId())
                .orderId(orderLine.getOrder().getId())
                .productId(orderLine.getProductId())
                .quantity(orderLine.getQuantity())
                .build();
    }
}
