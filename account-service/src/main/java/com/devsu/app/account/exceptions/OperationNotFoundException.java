package com.devsu.app.account.exceptions;

public class OperationNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public OperationNotFoundException(String message) {
        super(message);
    }

    public OperationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
