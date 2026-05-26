package com.io.banking.shared.exception;

public abstract class BankingException extends RuntimeException {
    protected BankingException(String message) {
        super(message);
    }
}
