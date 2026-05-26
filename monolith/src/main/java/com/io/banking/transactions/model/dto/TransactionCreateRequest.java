package com.io.banking.transactions.model.dto;

import com.io.banking.accounts.model.enums.Currency;
import com.io.banking.transactions.model.enums.TransactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionCreateRequest {

    @NotNull
    private UUID accountId;

    @NotNull
    private TransactionType transactionType;

    @NotNull
    private Currency currency;

    @NotNull
    @DecimalMin(value = "0.01", inclusive = true)
    private BigDecimal amount;
}
