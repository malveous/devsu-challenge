package com.devsu.app.account.dto;

import com.devsu.app.account.model.Account;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AccountDto {

    @Min(1)
    private int id;

    @NotBlank
    @Size(min = 16, message = "Account numbers have a minimum length of 16 characters")
    private String accountNumber;

    @NotBlank
    private String accountType;

    private double initialBalance;
    private boolean status;

    private CustomerDto customer;

    public AccountDto(Account accountEntity) {
        this.id = accountEntity.getId();
        this.accountNumber = accountEntity.getAccountNumber();
        this.accountType = accountEntity.getAccountType();
        this.initialBalance = accountEntity.getInitialBalance();
        this.status = accountEntity.isStatus();
        this.customer = CustomerDto.builder().personalId(accountEntity.getPersonalId()).build();
    }

}
