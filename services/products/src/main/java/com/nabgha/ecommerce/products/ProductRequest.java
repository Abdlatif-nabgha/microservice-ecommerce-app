package com.nabgha.ecommerce.products;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank(message = "Product name is required")
        @Size(max = 255, message = "Product name must be at most 255 characters")
        String name,
        @Size(max = 255, message = "Product description must be at most 255 characters")
        String description,
        @PositiveOrZero(message = "Available quantity must be zero or greater")
        double availableQuantity,
        @NotNull(message = "Product price is required")
        @DecimalMin(value = "0.00", message = "Product price must be zero or greater")
        @Digits(integer = 10, fraction = 2, message = "Product price must have at most 10 integer and 2 decimal digits")
        BigDecimal price,
        @NotBlank(message = "Category ID is required")
        @Size(max = 255, message = "Category ID must be at most 255 characters")
        String categoryId
) {
}
