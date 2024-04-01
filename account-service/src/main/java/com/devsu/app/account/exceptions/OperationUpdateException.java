package com.devsu.app.account.exceptions;

public class OperationUpdateException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public OperationUpdateException(String message) {
        super(message);
    }

    public OperationUpdateException(String message, Throwable cause) {
        super(message, cause);
    }

}
