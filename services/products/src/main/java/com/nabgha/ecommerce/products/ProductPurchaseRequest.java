package com.nabgha.ecommerce.products;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record ProductPurchaseRequest(
        @NotBlank(message = "Product ID is required")
        String productId,
        @Positive(message = "Purchase quantity must be greater than zero")
        double quantity
) {
}
