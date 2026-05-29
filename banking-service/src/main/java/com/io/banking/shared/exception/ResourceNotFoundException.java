package com.io.banking.shared.exception;

public class ResourceNotFoundException extends BankingException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
