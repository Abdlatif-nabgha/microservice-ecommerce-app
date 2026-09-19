package com.nabgha.ecommerce.payment;

import com.nabgha.ecommerce.notification.NotificationProducer;
import com.nabgha.ecommerce.notification.PaymentNotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final NotificationProducer notificationProducer;

    @Transactional
    public Payment processPayment(PaymentRequest request) {
        Payment savedPayment = paymentRepository.save(paymentMapper.toPayment(request));
        
        // send notification
        notificationProducer.sendNotification(
                PaymentNotificationRequest.builder()
                        .orderReference(request.orderReference())
                        .customerFirstName(request.customer().firstName())
                        .customerLastName(request.customer().lastName())
                        .customerEmail(request.customer().email())
                        .amount(request.amount())
                        .paymentMethod(request.paymentMethod())
                        .build()
        );
        
        return savedPayment;
    }
}
