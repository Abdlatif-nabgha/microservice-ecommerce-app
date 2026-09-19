package com.nabgha.ecommerce.product;


public record PurchaseRequest(
        String productId,
        double quantity
) {
}
