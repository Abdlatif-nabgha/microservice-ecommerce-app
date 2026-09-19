package com.nabgha.ecommerce.orderLines;


public record OrderLineRequest(
        String orderId,
        String productId,
        double quantity
){
}
