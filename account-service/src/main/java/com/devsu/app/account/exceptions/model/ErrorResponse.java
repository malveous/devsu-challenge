package com.devsu.app.account.exceptions.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ErrorResponse {

    private int statusCode;
    private String message;

}
