package com.io.banking.notifications.listener;

import com.io.banking.notifications.service.NotificationService;
import com.io.banking.shared.kafka.event.UserRegisteredEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationEventListener {

    private final NotificationService notificationService;

    @KafkaListener(
            topics = "#{T(com.io.banking.shared.kafka.KafkaTopic).USER_REGISTERED.value()}",
            groupId = "bank-service-group")
    public void onUserRegistered(UserRegisteredEvent event) {
        notificationService.notifyUserRegistered(event.userId(), event.email());
    }
}
