package com.io.banking.transactions.service.impl;

import com.io.banking.accounts.repository.AccountRepository;
import com.io.banking.shared.exception.AccountAccessDeniedException;
import com.io.banking.shared.exception.InvalidAmountException;
import com.io.banking.shared.exception.InsufficientFundsException;
import com.io.banking.shared.exception.ResourceNotFoundException;
import com.io.banking.shared.security.model.AuthenticatedUser;
import com.io.banking.transactions.mapper.TransactionMapper;
import com.io.banking.transactions.model.dto.TransactionCreateRequest;
import com.io.banking.transactions.model.dto.TransactionResponse;
import com.io.banking.transactions.model.entity.Transaction;
import com.io.banking.transactions.model.enums.TransactionStatus;
import com.io.banking.transactions.model.enums.TransactionType;
import com.io.banking.transactions.repository.TransactionRepository;
import com.io.banking.transactions.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public TransactionResponse createTransaction(TransactionCreateRequest request,
                                                 AuthenticatedUser user) {
        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException("Amount must be positive");
        }

        var account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        if (!account.getUser().getId().equals(user.getId())) {
            throw new AccountAccessDeniedException("Access denied to this account");
        }

        if (request.getTransactionType() == TransactionType.DEPOSIT) {
            account.setBalance(account.getBalance().add(request.getAmount()));
        }

        if (request.getTransactionType() == TransactionType.WITHDRAW) {
            if (account.getBalance().compareTo(request.getAmount()) < 0) {
                throw new InsufficientFundsException("Insufficient balance");
            }
            account.setBalance(account.getBalance().subtract(request.getAmount()));
        }

        var transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(request.getAmount());
        transaction.setCurrency(request.getCurrency());
        transaction.setType(request.getTransactionType());
        transaction.setStatus(TransactionStatus.SUCCESS);

        TransactionResponse response = TransactionMapper.toResponse(transactionRepository.save(transaction));
        log.info("Transaction created: id={}, type={}, amount={}, accountId={}",
                response.getTransactionId(), request.getTransactionType(), request.getAmount(), request.getAccountId());
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionResponse> getAllMyTransactions(Long userId) {
        return transactionRepository.findMyTransactions(userId)
                .stream()
                .map(TransactionMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TransactionResponse getTransactionById(UUID id, Long userId) {
        var transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));
        return TransactionMapper.toResponse(transaction);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransactionResponse> getAllTransactions(Pageable pageable) {
        return transactionRepository.findAll(pageable).map(TransactionMapper::toResponse);
    }
}
