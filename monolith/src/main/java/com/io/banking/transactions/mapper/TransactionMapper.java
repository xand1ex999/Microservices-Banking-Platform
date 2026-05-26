package com.io.banking.transactions.mapper;

import com.io.banking.transactions.model.dto.TransactionResponse;
import com.io.banking.transactions.model.entity.Transaction;

public final class TransactionMapper {

    private TransactionMapper() {}

    public static TransactionResponse toResponse(Transaction transaction) {
        if (transaction == null) {
            return null;
        }
        TransactionResponse response = new TransactionResponse();
        response.setTransactionId(transaction.getId());
        response.setAccountId(transaction.getAccount().getId());
        response.setAmount(transaction.getAmount());
        response.setCurrency(transaction.getCurrency());
        response.setTransactionType(transaction.getType());
        response.setStatus(transaction.getStatus());
        response.setCreatedAt(transaction.getCreatedAt());
        return response;
    }
}
