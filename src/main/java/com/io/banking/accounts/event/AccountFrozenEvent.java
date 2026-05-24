package com.io.banking.accounts.event;

import org.springframework.context.ApplicationEvent;

import java.util.UUID;

public class AccountFrozenEvent extends ApplicationEvent {

    private final UUID accountId;

    public AccountFrozenEvent(Object source, UUID accountId) {
        super(source);
        this.accountId = accountId;
    }

    public UUID getAccountId() {
        return accountId;
    }
}
