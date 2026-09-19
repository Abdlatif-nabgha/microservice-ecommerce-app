package com.nabgha.ecommerce.payment;

import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public Payment toPayment(PaymentRequest request) {
        return Payment.builder()
                .amount(request.amount())
                .paymentMethod(request.paymentMethod())
                .orderId(request.orderId())
                .build();
    }
}
