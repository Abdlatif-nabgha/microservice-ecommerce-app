package com.nabgha.ecommerce.payment;


import com.nabgha.ecommerce.customer.CustomerResponse;
import com.nabgha.ecommerce.orders.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String orderId,
        String orderReference,
        CustomerResponse customer
){}
