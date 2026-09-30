package com.nabgha.ecommerce.orderLines;

import com.nabgha.ecommerce.orders.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderLineMapper {

    OrderLineResponse toOrderLineResponse(OrderLine orderLine) {
        return OrderLineResponse.builder()
                .id(orderLine.getId())
                .orderId(orderLine.getOrder().getId())
                .productId(orderLine.getProductId())
                .quantity(orderLine.getQuantity())
                .build();
    }
}
