package com.nabgha.ecommerce.orders;


import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderResponse(
        String orderId,
        String reference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String customerId
) {
}
