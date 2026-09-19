package com.nabgha.ecommerce.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationProducer {

    private static final String PAYMENT_TOPIC = "payment-topic";

    private final KafkaTemplate<String, PaymentNotificationRequest> paymentKafkaTemplate;

    public void sendNotification(PaymentNotificationRequest request) {
        log.info(
                "Sending payment notification to topic {}: {}",
                PAYMENT_TOPIC,
                request
        );

        Message<PaymentNotificationRequest> message = MessageBuilder
                .withPayload(request)
                .setHeader(KafkaHeaders.TOPIC, PAYMENT_TOPIC)
                .build();

        paymentKafkaTemplate
                .send(message)
                .whenComplete((result, exception) -> {
                    if (exception != null) {
                        log.error(
                                "Failed to send payment notification to {}",
                                PAYMENT_TOPIC,
                                exception
                        );
                        return;
                    }

                    log.info(
                            "Payment notification sent successfully to topic={}, partition={}, offset={}",
                            result.getRecordMetadata().topic(),
                            result.getRecordMetadata().partition(),
                            result.getRecordMetadata().offset()
                    );
                });
    }
}