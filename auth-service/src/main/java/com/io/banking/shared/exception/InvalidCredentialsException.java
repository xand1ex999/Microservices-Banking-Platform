package com.io.banking.shared.exception;

public class InvalidCredentialsException extends BankingException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
