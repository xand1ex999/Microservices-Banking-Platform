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
        String key = String.valueOf(userId);
        UserRegisteredEvent event = new UserRegisteredEvent(userId, email, Instant.now());

        log.info("[KAFKA] Publishing UserRegisteredEvent: topic={}, key={}, userId={}",
                KafkaTopic.USER_REGISTERED, key, userId);

        kafkaTemplate.send(KafkaTopic.USER_REGISTERED, key, event)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("[KAFKA] Send failed: userId={}, error={}", userId, ex.getMessage(), ex);
                        return;
                    }
                    var meta = result.getRecordMetadata();
                    log.info("[KAFKA] Sent: userId={}, topic={}, partition={}, offset={}",
                            userId, meta.topic(), meta.partition(), meta.offset());
                });
    }
}
