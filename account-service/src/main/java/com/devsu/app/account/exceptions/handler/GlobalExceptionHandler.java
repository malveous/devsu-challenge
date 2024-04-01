package com.devsu.app.account.exceptions.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devsu.app.account.exceptions.AccountNotFoundException;
import com.devsu.app.account.exceptions.AccountUpdateException;
import com.devsu.app.account.exceptions.OperationNotFoundException;
import com.devsu.app.account.exceptions.OperationUpdateException;
import com.devsu.app.account.exceptions.OutOfBalanceException;
import com.devsu.app.account.exceptions.model.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAccountNotFoundException(
            AccountNotFoundException accountNotFoundException) {
        log.info("Account not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), accountNotFoundException.getMessage()));
    }

    @ExceptionHandler(AccountUpdateException.class)
    public ResponseEntity<ErrorResponse> handleAccountUpdateException(AccountUpdateException accountUpdateException) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), accountUpdateException.getMessage()));
    }

    @ExceptionHandler(OperationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleOperationNotFoundException(
            OperationNotFoundException operationNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), operationNotFoundException.getMessage()));
    }

    @ExceptionHandler(OperationUpdateException.class)
    public ResponseEntity<ErrorResponse> handleOperationUpdateException(
            OperationUpdateException operationUpdateException) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), operationUpdateException.getMessage()));
    }

    @ExceptionHandler(OutOfBalanceException.class)
    public ResponseEntity<ErrorResponse> handleOutOfBalanceException(OutOfBalanceException outOfBalanceException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), outOfBalanceException.getMessage()));
    }

}
