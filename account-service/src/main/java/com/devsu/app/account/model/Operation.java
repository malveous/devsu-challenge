package com.devsu.app.account.model;

import java.time.Instant;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.devsu.app.account.dto.OperationDto;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Operation {

    public static final String DEFAULT_ADDING_FUNDS_OPERATION_TYPE = "ADDING_FUNDS";
    public static final String DEFAULT_REDUCING_BALANCE_OPERATION_TYPE = "REDUCING_BALANCE";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Instant date;
    private String type; // Ideally should be an enum or store possible values in another table
    private double value;
    private double balance;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Account account;

    public Operation(OperationDto operationDto, Account account) {
        this.id = operationDto.getId();
        this.date = operationDto.getDate();
        this.type = operationDto.getType();
        this.value = operationDto.getValue();
        this.balance = operationDto.getBalance();
        this.account = account;
    }

}
