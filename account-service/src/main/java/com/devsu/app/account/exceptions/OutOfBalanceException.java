package com.devsu.app.account.exceptions;

public class OutOfBalanceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public OutOfBalanceException(String message) {
        super(message);
    }

    public OutOfBalanceException(String message, Throwable cause) {
        super(message, cause);
    }

}
