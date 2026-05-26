package com.io.banking.auth.publisher;

import com.io.banking.shared.kafka.KafkaTopic;
import com.io.banking.shared.kafka.event.UserRegisteredEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishUserRegistered(Long userId, String email) {
        kafkaTemplate.send(KafkaTopic.USER_REGISTERED.value(), String.valueOf(userId),
                        new UserRegisteredEvent(userId, email, Instant.now()))
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("[KAFKA] Failed to send UserRegisteredEvent for userId={}: {}", userId, ex.getMessage());
                    } else {
                        log.info("[KAFKA] UserRegisteredEvent sent for userId={}, offset={}",
                                userId, result.getRecordMetadata().offset());
                    }
                });
    }
}
