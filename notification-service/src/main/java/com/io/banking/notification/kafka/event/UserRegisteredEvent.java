package com.io.banking.notification.kafka.event;

import java.time.Instant;

public record UserRegisteredEvent(

        Long userId,

        String email,

        Instant registeredAt

) {
}
