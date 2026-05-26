package com.io.banking.accounts.service;

import com.io.banking.accounts.model.dto.AccountCreateRequest;
import com.io.banking.accounts.model.dto.AccountResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface AccountService {

    AccountResponse createAccount(AccountCreateRequest request, Long userId);

    List<AccountResponse> getUserAccounts(Long userId);

    AccountResponse getAccountById(UUID accountId);

    Page<AccountResponse> getAllAccounts(Pageable pageable);

    void freezeAccount(UUID accountId);

    void unfreezeAccount(UUID accountId);

}
