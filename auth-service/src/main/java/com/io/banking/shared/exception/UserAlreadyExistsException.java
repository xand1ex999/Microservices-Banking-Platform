package com.io.banking.shared.exception;

public class UserAlreadyExistsException extends BankingException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
