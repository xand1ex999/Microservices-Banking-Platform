package com.io.banking.transactions.service;

import com.io.banking.shared.security.model.AuthenticatedUser;
import com.io.banking.transactions.model.dto.TransactionCreateRequest;
import com.io.banking.transactions.model.dto.TransactionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface TransactionService {

    TransactionResponse createTransaction(TransactionCreateRequest request, AuthenticatedUser user);

    List<TransactionResponse> getAllMyTransactions(Long userId);

    TransactionResponse getTransactionById(UUID id, Long userId);

    Page<TransactionResponse> getAllTransactions(Pageable pageable);

}
