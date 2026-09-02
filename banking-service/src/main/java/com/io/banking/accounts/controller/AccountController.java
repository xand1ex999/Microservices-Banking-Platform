package com.io.banking.accounts.controller;

import com.io.banking.accounts.model.dto.AccountCreateRequest;
import com.io.banking.accounts.model.dto.AccountResponse;
import com.io.banking.accounts.service.AccountService;
import com.io.banking.shared.security.model.AuthenticatedUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(
            @Valid @RequestBody AccountCreateRequest request,
            @AuthenticationPrincipal AuthenticatedUser user) {
        return ResponseEntity.ok(accountService.createAccount(request, user.getId()));
    }

    @GetMapping("/my")
    public List<AccountResponse> getMyAccounts(@AuthenticationPrincipal AuthenticatedUser user) {
        return accountService.getUserAccounts(user.getId());
    }

    @GetMapping("/{accountId}")
    @PreAuthorize("hasRole('ADMIN')")
    public AccountResponse getAccountById(@PathVariable UUID accountId) {
        return accountService.getAccountById(accountId);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<AccountResponse> getAllAccounts(Pageable pageable) {
        return accountService.getAllAccounts(pageable);
    }

    @PatchMapping("/{accountId}/freeze")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void freezeAccount(@PathVariable UUID accountId) {
        accountService.freezeAccount(accountId);
    }

    @PatchMapping("/{accountId}/unfreeze")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void unfreezeAccount(@PathVariable UUID accountId) {
        accountService.unfreezeAccount(accountId);
    }

}
