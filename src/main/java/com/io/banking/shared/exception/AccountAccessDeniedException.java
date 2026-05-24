package com.io.banking.shared.exception;

public class AccountAccessDeniedException extends BankingException {
    public AccountAccessDeniedException(String message) {
        super(message);
    }
}
