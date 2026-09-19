package com.nabgha.ecommerce.products;


public record ProductPurchaseRequest(
        String productId,
        double quantity
) {
}
