package com.nabgha.ecommerce.products;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductResponse(
        String id,
        String name,
        String description,
        double availableQuantity,
        BigDecimal price,
        String categoryId,
        String categoryName
) {
}
