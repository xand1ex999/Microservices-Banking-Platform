package com.io.banking.notification.listener;

import com.io.banking.notification.kafka.event.UserRegisteredEvent;
import com.io.banking.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationEventListener {

    private final NotificationService notificationService;

    @KafkaListener(
            topics = "#{T(com.io.banking.notification.kafka.KafkaTopic).USER_REGISTERED.value()}",
            groupId = "notification-service-group")
    public void onUserRegistered(UserRegisteredEvent event) {
        notificationService.notifyUserRegistered(event.userId(), event.email());
    }
}
