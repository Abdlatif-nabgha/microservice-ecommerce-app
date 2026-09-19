package com.nabgha.ecommerce.kafka;


import com.nabgha.ecommerce.customer.CustomerResponse;
import com.nabgha.ecommerce.orders.PaymentMethod;
import com.nabgha.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {}
