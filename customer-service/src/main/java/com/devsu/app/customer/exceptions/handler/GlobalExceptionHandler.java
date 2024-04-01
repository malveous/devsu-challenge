package com.devsu.app.customer.exceptions.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devsu.app.customer.exceptions.CustomerNotFoundException;
import com.devsu.app.customer.exceptions.CustomerRequiredDataException;
import com.devsu.app.customer.exceptions.CustomerUpdateException;
import com.devsu.app.customer.exceptions.model.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCustomerNotFoundException(
            CustomerNotFoundException customerNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), customerNotFoundException.getMessage()));
    }

    @ExceptionHandler(CustomerRequiredDataException.class)
    public ResponseEntity<ErrorResponse> handleCustomerRequiredDataException(
            CustomerRequiredDataException customerRequiredDataException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), customerRequiredDataException.getMessage()));
    }

    @ExceptionHandler(CustomerUpdateException.class)
    public ResponseEntity<ErrorResponse> handleCustomerUpdateException(
            CustomerUpdateException customerUpdateException) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), customerUpdateException.getMessage()));
    }

}
