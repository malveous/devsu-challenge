package com.devsu.app.customer.exceptions;

public class CustomerRequiredDataException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CustomerRequiredDataException(String message) {
        super(message);
    }

    public CustomerRequiredDataException(String message, Throwable cause) {
        super(message, cause);
    }

}
