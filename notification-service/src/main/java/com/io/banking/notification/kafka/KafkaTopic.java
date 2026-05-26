package com.io.banking.notification.kafka;

public enum KafkaTopic {

    USER_REGISTERED("user.registered");

    private final String value;

    KafkaTopic(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
