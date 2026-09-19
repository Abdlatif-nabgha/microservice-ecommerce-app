package com.nabgha.ecommerce.products;


import java.math.BigDecimal;

public record ProductRequest(
        String name,
        String description,
        double availableQuantity,
        BigDecimal price,
        String categoryId
) {
}
