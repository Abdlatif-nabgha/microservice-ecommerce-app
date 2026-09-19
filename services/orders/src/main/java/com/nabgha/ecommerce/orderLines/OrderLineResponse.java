package com.nabgha.ecommerce.orderLines;


import lombok.Builder;

@Builder
public record OrderLineResponse(
        String id,
        String orderId,
        String productId,
        double quantity
) {
}
