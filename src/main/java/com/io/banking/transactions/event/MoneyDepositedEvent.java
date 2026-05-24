package com.io.banking.transactions.event;

import org.springframework.context.ApplicationEvent;

import java.math.BigDecimal;
import java.util.UUID;

public class MoneyDepositedEvent extends ApplicationEvent {

    private final UUID accountId;
    private final Long userId;
    private final BigDecimal amount;

    public MoneyDepositedEvent(Object source, UUID accountId, Long userId, BigDecimal amount) {
        super(source);
        this.accountId = accountId;
        this.userId = userId;
        this.amount = amount;
    }

    public UUID getAccountId() { return accountId; }
    public Long getUserId() { return userId; }
    public BigDecimal getAmount() { return amount; }
}
