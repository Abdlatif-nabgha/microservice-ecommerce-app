package com.nabgha.ecommerce.kafka;

import com.nabgha.ecommerce.email.EmailService;
import com.nabgha.ecommerce.kafka.order.OrderConfirmation;
import com.nabgha.ecommerce.kafka.payment.PaymentConfirmation;
import com.nabgha.ecommerce.notification.Notification;
import com.nabgha.ecommerce.notification.NotificationRepository;
import com.nabgha.ecommerce.notification.NotificationType;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.nabgha.ecommerce.notification.NotificationType.ORDER_CONFIRMATION;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final NotificationRepository notificationRepository;
    private final EmailService emailService;

    @KafkaListener(topics = "payment-topic")
    public void consumePaymentSuccessNotification(PaymentConfirmation paymentConfirmation) throws MessagingException {
        log.info("Consuming the message from payment-topic Topic: {}", paymentConfirmation);

        notificationRepository.save(
                Notification.builder()
                        .type(NotificationType.PAYMENT_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .paymentConfirmation(paymentConfirmation)
                        .build()
        );

        //  send email
        var customerName = paymentConfirmation.customerFirstName();
        var customerEmail = paymentConfirmation.customerEmail();
        var amount = paymentConfirmation.amount();
        var orderReference = paymentConfirmation.orderReference();
        emailService.sendPaymentSuccessEmail(
                customerEmail,
                customerName,
                amount,
                orderReference
        );
    }


    @KafkaListener(topics = "order-topic")
    public void consumeOrderConfirmationNotification(OrderConfirmation orderConfirmation) throws MessagingException {
        log.info("Consuming the message from order-topic Topic: {}", orderConfirmation);

        notificationRepository.save(
                Notification.builder()
                        .type(ORDER_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .orderConfirmation(orderConfirmation)
                        .build()
        );

        // send email
        var customerName = orderConfirmation.customer().firstName();
        var customerEmail = orderConfirmation.customer().email();
        var amount = orderConfirmation.amount();
        var orderReference = orderConfirmation.orderReference();
        var products = orderConfirmation.products();
        emailService.sendOrderConfirmationEmail(
                customerEmail,
                customerName,
                amount,
                orderReference,
                products
        );

    }
}
