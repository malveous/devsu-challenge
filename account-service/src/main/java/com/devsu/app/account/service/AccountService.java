package com.devsu.app.account.service;

import java.util.List;

import com.devsu.app.account.model.Account;

public interface AccountService {

	List<Account> listAllAccounts();
	
	Account findAccountById(int id);
	
	Account findAccountByAccountNumber(String accountNumber);
	
	int register(Account newAccount);
	
	int update(Account existingAccount);
	
	void delete(int id);
	
}
