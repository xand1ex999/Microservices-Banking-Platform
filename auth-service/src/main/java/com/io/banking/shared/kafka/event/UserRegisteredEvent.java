package com.io.banking.shared.kafka.event;

import java.time.Instant;

public record UserRegisteredEvent(
        Long userId,
        String email,
        Instant registeredAt
) {}
