package com.nabgha.ecommerce.products;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductPurchaseResponse(
        String productId,
        String name,
        String description,
        BigDecimal price,
        double quantity
) {
}
