package com.nabgha.ecommerce.notification;

import com.nabgha.ecommerce.payment.PaymentMethod;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PaymentNotificationRequest(
        String orderReference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String customerFirstName,
        String customerLastName,
        String customerEmail
) {
}
