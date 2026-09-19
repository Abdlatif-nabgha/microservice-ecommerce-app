package com.nabgha.ecommerce.orders;


import com.nabgha.ecommerce.product.PurchaseRequest;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequest(
        String reference,
        @Positive
        BigDecimal amount,
        @NotNull(message = "Payment cannot be null")
        PaymentMethod paymentMethod,
        @NotNull(message = "Customer should be present")
        String customerId,
        @NotEmpty(message = "You should at least purchase one product")
        List<PurchaseRequest> products
        )
{}
