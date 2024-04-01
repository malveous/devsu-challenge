package com.devsu.app.account.model;

import com.devsu.app.account.dto.AccountDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String accountNumber;
    private String accountType; // Ideally should be handled with another table or an enum
    private double initialBalance;
    private boolean status;
    private String personalId;

    public Account(AccountDto accountDto) {
        this.id = accountDto.getId();
        this.accountNumber = accountDto.getAccountNumber();
        this.accountType = accountDto.getAccountType();
        this.initialBalance = accountDto.getInitialBalance();
        this.status = accountDto.isStatus();
        this.personalId = accountDto.getCustomer().getPersonalId();

    }

}
