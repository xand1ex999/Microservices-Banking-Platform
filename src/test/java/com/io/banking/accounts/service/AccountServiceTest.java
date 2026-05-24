package com.io.banking.accounts.service;

import com.io.banking.accounts.model.dto.AccountCreateRequest;
import com.io.banking.accounts.model.dto.AccountResponse;
import com.io.banking.accounts.model.entity.Account;
import com.io.banking.accounts.model.enums.AccountStatus;
import com.io.banking.accounts.model.enums.Currency;
import com.io.banking.accounts.repository.AccountRepository;
import com.io.banking.accounts.service.impl.AccountServiceImpl;
import com.io.banking.users.model.entity.User;
import com.io.banking.users.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private AccountServiceImpl accountService;

    private Account mockAccount(AccountStatus status) {
        Account account = new Account();
        account.setId(UUID.randomUUID());
        account.setBalance(BigDecimal.TEN);
        account.setCurrency(Currency.USD);
        account.setStatus(status);
        account.setCreatedAt(Instant.now());
        return account;
    }

    @Test
    void createAccount_success() {
        Long userId = 1L;

        User user = new User();
        user.setId(userId);

        AccountCreateRequest request = new AccountCreateRequest();
        request.setCurrency(Currency.USD);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(accountRepository.save(any(Account.class))).thenAnswer(inv -> inv.getArgument(0));

        AccountResponse response = accountService.createAccount(request, userId);

        assertNotNull(response);
        assertEquals(Currency.USD, response.getCurrency());
        assertEquals(BigDecimal.ZERO, response.getBalance());

        verify(userRepository).findById(userId);
        verify(accountRepository).save(any(Account.class));
    }

    @Test
    void createAccount_userNotFound() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> accountService.createAccount(new AccountCreateRequest(), userId));

        assertTrue(ex.getMessage().contains("not found"));
    }

    @Test
    void getUserAccounts_success() {
        Long userId = 1L;

        Account account1 = mockAccount(AccountStatus.ACTIVE);
        Account account2 = mockAccount(AccountStatus.FROZEN);

        when(accountRepository.findAllByUserId(userId)).thenReturn(List.of(account1, account2));

        List<AccountResponse> result = accountService.getUserAccounts(userId);

        assertEquals(2, result.size());
    }

    @Test
    void getUserAccounts_emptyList() {
        Long userId = 1L;

        when(accountRepository.findAllByUserId(userId)).thenReturn(List.of());

        List<AccountResponse> result = accountService.getUserAccounts(userId);

        assertTrue(result.isEmpty());
    }

    @Test
    void getAccountById_success() {
        UUID accountId = UUID.randomUUID();
        Account account = mockAccount(AccountStatus.ACTIVE);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        AccountResponse response = accountService.getAccountById(accountId);

        assertNotNull(response);
        assertEquals(account.getStatus(), response.getStatus());
    }

    @Test
    void getAccountById_notFound() {
        UUID accountId = UUID.randomUUID();

        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> accountService.getAccountById(accountId));

        assertTrue(ex.getMessage().contains("not found"));
    }

    @Test
    void getAllAccounts_success() {
        Account account1 = mockAccount(AccountStatus.ACTIVE);
        Account account2 = mockAccount(AccountStatus.FROZEN);

        Page<Account> page = new PageImpl<>(List.of(account1, account2));

        when(accountRepository.findAll(any(PageRequest.class))).thenReturn(page);

        Page<AccountResponse> result = accountService.getAllAccounts(PageRequest.of(0, 10));

        assertEquals(2, result.getTotalElements());
    }

    @Test
    void freezeAccount_activeToFrozen() {
        UUID accountId = UUID.randomUUID();
        Account account = mockAccount(AccountStatus.ACTIVE);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        accountService.freezeAccount(accountId);

        assertEquals(AccountStatus.FROZEN, account.getStatus());
        verify(accountRepository).save(account);
    }

    @Test
    void freezeAccount_alreadyFrozen() {
        UUID accountId = UUID.randomUUID();
        Account account = mockAccount(AccountStatus.FROZEN);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        accountService.freezeAccount(accountId);

        verify(accountRepository, never()).save(any());
    }

    @Test
    void unfreezeAccount_success() {
        UUID accountId = UUID.randomUUID();
        Account account = mockAccount(AccountStatus.FROZEN);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        accountService.unfreezeAccount(accountId);

        assertEquals(AccountStatus.ACTIVE, account.getStatus());
        verify(accountRepository).save(account);
    }

    @Test
    void unfreezeAccount_notFrozen() {
        UUID accountId = UUID.randomUUID();
        Account account = mockAccount(AccountStatus.ACTIVE);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        assertThrows(IllegalStateException.class, () -> accountService.unfreezeAccount(accountId));
    }
}
