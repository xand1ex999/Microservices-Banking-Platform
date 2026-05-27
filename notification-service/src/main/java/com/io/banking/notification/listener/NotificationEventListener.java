package com.io.banking.notification.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.io.banking.notification.kafka.KafkaTopic;
import com.io.banking.notification.kafka.event.UserRegisteredEvent;
import com.io.banking.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationEventListener {

    private final NotificationService notificationService;
    private final ObjectMapper kafkaObjectMapper;

    @KafkaListener(topics = KafkaTopic.USER_REGISTERED)
    public void onUserRegistered(String rawMessage) {
        log.info("[NOTIFICATION] Received: {}", rawMessage);

        try {
            UserRegisteredEvent event = kafkaObjectMapper.readValue(rawMessage, UserRegisteredEvent.class);
            notificationService.notifyUserRegistered(event.userId(), event.email());
            log.info("[NOTIFICATION] Processed: userId={}", event.userId());
        } catch (Exception e) {
            log.error("[NOTIFICATION] Failed to process: {}", e.getMessage(), e);
        }
    }
}
