package com.devsu.app.customer.exceptions;

public class CustomerNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CustomerNotFoundException(String message) {
        super(message);
    }

    public CustomerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
