package com.devsu.app.customer.exceptions;

public class CustomerUpdateException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public CustomerUpdateException(String message) {
		super(message);
	}
	
	public CustomerUpdateException(String message, Throwable cause) {
		super(message, cause);
	}

}
