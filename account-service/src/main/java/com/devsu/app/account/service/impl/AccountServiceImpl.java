package com.devsu.app.account.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.devsu.app.account.exceptions.AccountNotFoundException;
import com.devsu.app.account.exceptions.AccountUpdateException;
import com.devsu.app.account.integration.service.CustomerIntegrationService;
import com.devsu.app.account.model.Account;
import com.devsu.app.account.repository.AccountRepository;
import com.devsu.app.account.service.AccountService;
import com.devsu.app.account.util.AccountDataUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

	private final AccountRepository accountRepository;
	private final CustomerIntegrationService customerIntegrationService;

	@Override
	public List<Account> listAllAccounts() {
		var accounts = (List<Account>) this.accountRepository.findAll();
		
		if (CollectionUtils.isEmpty(accounts)) {
			throw new AccountNotFoundException("No accounts were available for listing.");
		}
		
		return accounts;
	}

	@Override
	public Account findAccountById(int id) {
		var account = this.accountRepository.findById(id);
		
		if (account.isEmpty()) {
			throw new AccountNotFoundException("No account found for id: " + id);
		}
		
		return account.get();
	}

	@Override
	public Account findAccountByAccountNumber(String accountNumber) {
		var account = this.accountRepository.findByAccountNumber(accountNumber);
		
		if (account.isEmpty()) {
			throw new AccountNotFoundException("No account found for account number: " + accountNumber);
		}
		
		return account.get();
	}

	@Override
	public int register(Account newAccount) throws AccountNotFoundException{
		var generatedAccountId = -1;
		var customerPersonalIdForAccount = newAccount.getPersonalId();
		
		if (StringUtils.isAnyBlank(newAccount.getAccountType(), customerPersonalIdForAccount)) {
			throw new AccountUpdateException("The account type should be specified and associated to an existing customer");
		} else if(newAccount.getInitialBalance() < 0) {
			throw new AccountUpdateException("No negative balance is allowed for account");
		} else {
			var customerData = this.customerIntegrationService.getCustomerDataByPersonalId(customerPersonalIdForAccount);
			
			if (Objects.isNull(customerData)) {
				throw new AccountUpdateException("The personal ID specified does not match a customer for account opening.");
			}
			
			newAccount.setAccountNumber(AccountDataUtil.generateAccountNumber());
			newAccount.setStatus(Boolean.TRUE);
			generatedAccountId = this.accountRepository.save(newAccount).getId();
		}
		
		return generatedAccountId;
	}

	@Override
	public int update(Account targetAccount) throws AccountNotFoundException {
		var affectedAccount = -1;
		var accountId = targetAccount.getId();
		var accountNumber = targetAccount.getAccountNumber();
		Optional<Account> existingAccount = Optional.empty();
		
		if (accountId > 0) {
			existingAccount = this.accountRepository.findById(accountId);
		} else if (StringUtils.isNotBlank(accountNumber)) {
			existingAccount = this.accountRepository.findByAccountNumber(accountNumber);
		} else {
			throw new AccountUpdateException("Account number or id must be specified for this request");
		}
		
		if (existingAccount.isEmpty()) {
			throw new AccountUpdateException("No account was found for update process.");
		}
		
		// We cannot change the account type and the client, usually we open a new account and remove the old one
		// This also applies for the customer reference, so these are not updatable values
		if (targetAccount.getInitialBalance() < 0 || !StringUtils.isAnyBlank(targetAccount.getAccountType(), targetAccount.getPersonalId())) {
			throw new AccountUpdateException("Values selected for update are not allowed for this process");
		}
		
		var accountData = existingAccount.get();
		accountData.setInitialBalance(targetAccount.getInitialBalance());
		affectedAccount = this.accountRepository.save(accountData).getId();
		
		return affectedAccount;
	}

	@Override
	public void delete(int id) throws AccountNotFoundException {
		try {
			this.accountRepository.deleteById(id);
		} catch (Exception e) {
			throw new AccountUpdateException("An exception occurred while trying to delete account information", e);
		}
		
	}
	
	
	
}
