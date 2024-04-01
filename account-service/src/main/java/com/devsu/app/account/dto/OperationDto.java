package com.devsu.app.account.dto;

import java.time.Instant;

import com.devsu.app.account.model.Operation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OperationDto {

    private int id;
    private Instant date;
    private String type;
    private double value;
    private double balance;
    private int accountId;
    private String accountNumber;

    public OperationDto(Operation operationEntity) {
        this.id = operationEntity.getId();
        this.date = operationEntity.getDate();
        this.type = operationEntity.getType();
        this.value = operationEntity.getValue();
        this.balance = operationEntity.getBalance();
        this.accountId = operationEntity.getAccount().getId();
        this.accountNumber = operationEntity.getAccount().getAccountNumber();
    }

}
