package com.nabgha.ecommerce.payment;


import lombok.Builder;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.Valid;
import java.math.BigDecimal;

@Builder
public record PaymentRequest(
        @NotNull(message = "Order ID is required")
        String orderId,
        @NotNull(message = "Payment method is required")
        PaymentMethod paymentMethod,
        @Positive(message = "Amount should be positive")
        BigDecimal amount,
        @NotNull(message = "Order reference is required")
        String orderReference,
        @Valid
        @NotNull(message = "Customer is required")
        Customer customer
) {}
