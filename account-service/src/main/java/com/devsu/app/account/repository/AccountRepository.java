package com.devsu.app.account.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devsu.app.account.model.Account;

public interface AccountRepository extends JpaRepository<Account, Integer>{

	Optional<Account> findByAccountNumber(String accountNumber);
	
	Optional<Account> findByPersonalId(String personalId);
	
}
