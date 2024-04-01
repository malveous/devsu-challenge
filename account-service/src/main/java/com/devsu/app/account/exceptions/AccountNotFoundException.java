package com.devsu.app.account.exceptions;

public class AccountNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public static final String DEFAULT_MSG = "No account was found for the specified number";

    public AccountNotFoundException() {
        super(DEFAULT_MSG);
    }

    public AccountNotFoundException(String message) {
        super(message);
    }

    public AccountNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
