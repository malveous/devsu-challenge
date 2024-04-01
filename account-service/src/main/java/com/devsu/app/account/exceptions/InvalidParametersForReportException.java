package com.devsu.app.account.exceptions;

public class InvalidParametersForReportException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidParametersForReportException(String message) {
        super(message);
    }

    public InvalidParametersForReportException(String message, Throwable cause) {
        super(message, cause);
    }

}
