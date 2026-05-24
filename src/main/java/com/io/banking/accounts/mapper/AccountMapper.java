package com.io.banking.accounts.mapper;

import com.io.banking.accounts.model.dto.AccountResponse;
import com.io.banking.accounts.model.entity.Account;

public final class AccountMapper {

    private AccountMapper() {}

    public static AccountResponse toResponse(Account account) {
        AccountResponse dto = new AccountResponse();
        dto.setId(account.getId());
        dto.setCurrency(account.getCurrency());
        dto.setBalance(account.getBalance());
        dto.setStatus(account.getStatus());
        dto.setCreatedAt(account.getCreatedAt());
        return dto;
    }
}
