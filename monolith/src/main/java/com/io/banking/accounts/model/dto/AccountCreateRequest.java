package com.io.banking.accounts.model.dto;

import com.io.banking.accounts.model.enums.Currency;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountCreateRequest {

    @NotNull
    private Currency currency;
}
