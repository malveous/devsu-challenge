package com.devsu.app.account.service;

import java.util.List;

import com.devsu.app.account.dto.AccountDto;

public interface AccountService {

    List<AccountDto> listAllAccounts();

    AccountDto findAccountById(int id);

    AccountDto findAccountByAccountNumber(String accountNumber);

    int register(AccountDto newAccount);

    int update(AccountDto existingAccount);

    void delete(int id);

}
