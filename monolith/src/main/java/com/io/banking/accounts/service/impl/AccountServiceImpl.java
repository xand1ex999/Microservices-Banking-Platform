package com.io.banking.accounts.service.impl;

import com.io.banking.accounts.mapper.AccountMapper;
import com.io.banking.accounts.model.dto.AccountCreateRequest;
import com.io.banking.accounts.model.dto.AccountResponse;
import com.io.banking.accounts.model.entity.Account;
import com.io.banking.accounts.model.enums.AccountStatus;
import com.io.banking.accounts.repository.AccountRepository;
import com.io.banking.accounts.service.AccountService;
import com.io.banking.shared.exception.ResourceNotFoundException;
import com.io.banking.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public AccountResponse createAccount(AccountCreateRequest request, Long userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id" + userId + "not found"));

        var account = new Account();
        account.setUser(user);
        account.setCurrency(request.getCurrency());
        account.setBalance(BigDecimal.ZERO);
        account.setStatus(AccountStatus.ACTIVE);
        account.setCreatedAt(Instant.now());

        AccountResponse response = AccountMapper.toResponse(accountRepository.save(account));
        log.info("Account created: id={}, currency={}, userId={}", response.getId(), request.getCurrency(), userId);
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountResponse> getUserAccounts(Long userId) {
        return accountRepository.findAllByUserId(userId)
                .stream()
                .map(AccountMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AccountResponse getAccountById(UUID accountId) {
        return accountRepository.findById(accountId)
                .map(AccountMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Account with id" + accountId + "not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AccountResponse> getAllAccounts(Pageable pageable) {
        return accountRepository.findAll(pageable).map(AccountMapper::toResponse);
    }

    @Override
    @Transactional
    public void freezeAccount(UUID accountId) {
        var account = loadAccount(accountId);

        if (account.getStatus() == AccountStatus.FROZEN) {
            log.warn("Account {} is already frozen, skipping", accountId);
            return;
        }

        if (account.getStatus() != AccountStatus.ACTIVE
                && account.getStatus() != AccountStatus.INACTIVE) {
            throw new IllegalStateException(
                    "Cannot freeze account with status: " + account.getStatus());
        }

        account.setStatus(AccountStatus.FROZEN);
        accountRepository.save(account);
        log.info("Account frozen: id={}", accountId);
    }

    @Override
    @Transactional
    public void unfreezeAccount(UUID accountId) {
        var account = loadAccount(accountId);

        if (account.getStatus() != AccountStatus.FROZEN) {
            throw new IllegalStateException(
                    "Cannot unfreeze account with status: " + account.getStatus());
        }

        account.setStatus(AccountStatus.ACTIVE);
        accountRepository.save(account);
        log.info("Account unfrozen: id={}", accountId);
    }

    private Account loadAccount(UUID id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account with id " + id + " not found"));
    }
}
