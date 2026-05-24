package com.io.banking.transactions.model.dto;

import com.io.banking.accounts.model.enums.Currency;
import com.io.banking.transactions.model.enums.TransactionStatus;
import com.io.banking.transactions.model.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {
    private UUID transactionId;
    private UUID accountId;
    private BigDecimal amount;
    private Currency currency;
    private TransactionType transactionType;
    private TransactionStatus status;
    private Instant createdAt;
}
