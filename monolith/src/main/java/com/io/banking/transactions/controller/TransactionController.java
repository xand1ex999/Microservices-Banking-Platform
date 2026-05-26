package com.io.banking.transactions.controller;

import com.io.banking.shared.security.model.AuthenticatedUser;
import com.io.banking.transactions.model.dto.TransactionCreateRequest;
import com.io.banking.transactions.model.dto.TransactionResponse;
import com.io.banking.transactions.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(
            @Valid @RequestBody TransactionCreateRequest request,
            @AuthenticationPrincipal AuthenticatedUser user) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(transactionService.createTransaction(request, user));
    }

    @GetMapping("/my")
    public ResponseEntity<List<TransactionResponse>> getAllMyTransactions(
            @AuthenticationPrincipal AuthenticatedUser user) {
        return ResponseEntity.ok(transactionService.getAllMyTransactions(user.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> getTransactionById(
            @PathVariable UUID id,
            @AuthenticationPrincipal AuthenticatedUser user) {
        return ResponseEntity.ok(transactionService.getTransactionById(id, user.getId()));
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<TransactionResponse>> getAllTransactions(Pageable pageable) {
        return ResponseEntity.ok(transactionService.getAllTransactions(pageable));
    }
}
