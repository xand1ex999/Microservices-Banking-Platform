package com.io.banking.notification.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.io.banking.notification.kafka.KafkaTopic;
import com.io.banking.notification.kafka.event.UserRegisteredEvent;
import com.io.banking.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationEventListener {

    private final NotificationService notificationService;
    private final ObjectMapper kafkaObjectMapper;

    @KafkaListener(topics = KafkaTopic.USER_REGISTERED)
    public void onUserRegistered(
            String rawMessage,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
            @Header(KafkaHeaders.OFFSET) long offset) {

        log.info("[KAFKA] Received USER_REGISTERED: partition={}, offset={}", partition, offset);

        try {
            UserRegisteredEvent event = kafkaObjectMapper.readValue(rawMessage, UserRegisteredEvent.class);
            notificationService.notifyUserRegistered(event.userId(), event.email());
            log.info("[KAFKA] Processed USER_REGISTERED: userId={}", event.userId());
        } catch (Exception e) {
            log.error("[KAFKA] Failed to process USER_REGISTERED: partition={}, offset={}, error={}",
                    partition, offset, e.getMessage(), e);
            throw new RuntimeException("Failed to process USER_REGISTERED event", e);
        }
    }
}
