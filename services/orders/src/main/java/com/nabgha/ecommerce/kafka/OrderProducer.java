package com.nabgha.ecommerce.kafka;

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
public class OrderProducer {

    private static final String ORDER_TOPIC = "order-topic";

    private final KafkaTemplate<Object, Object> kafkaTemplate;

    public void sendOrderConfirmation(OrderConfirmation orderConfirmation) {
        log.info("Sending order confirmation to topic {}: {}", ORDER_TOPIC, orderConfirmation);

        Message<OrderConfirmation> message = MessageBuilder
                .withPayload(orderConfirmation)
                .setHeader(KafkaHeaders.TOPIC, ORDER_TOPIC)
                .build();

        kafkaTemplate
                .send(message)
                .whenComplete((result, exception) -> {
                    if ( exception != null) {
                        log.error("Failed to send order confirmation to {}", ORDER_TOPIC, exception);
                        return;
                    }
                    log.info(
                            "Order confirmation sent successfully to topic={}, partition={}, offset={}",
                            result.getRecordMetadata().topic(),
                            result.getRecordMetadata().partition(),
                            result.getRecordMetadata().offset());
                });
    }
}
