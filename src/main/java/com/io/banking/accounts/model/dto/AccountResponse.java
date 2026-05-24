package com.io.banking.accounts.model.dto;

import com.io.banking.accounts.model.enums.AccountStatus;
import com.io.banking.accounts.model.enums.Currency;
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
public class AccountResponse {
    private UUID id;
    private Currency currency;
    private BigDecimal balance;
    private AccountStatus status;
    private Instant createdAt;
}
