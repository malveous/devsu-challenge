package com.devsu.app.account.api.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsu.app.account.model.Account;
import com.devsu.app.account.service.AccountService;

@RestController
@RequestMapping("/api/account")
public class AccountRestApi {

	@Autowired
	private AccountService accountService;
	
	@GetMapping
	public List<Account> listAllAccounts() {
		return this.accountService.listAllAccounts();
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<Account> findAccountById(@PathVariable(value = "id") int id) {
		return ResponseEntity.ok(this.accountService.findAccountById(id));
	}
	
	@GetMapping("/accountNumber/{accountNumber}")
	public ResponseEntity<Account> findAccountByAccountNumber(@PathVariable(value = "accountNumber") String accountNumber) {
		return ResponseEntity.ok(this.accountService.findAccountByAccountNumber(accountNumber));
	}
	
	@PostMapping("/newAccount")
	public ResponseEntity<Integer> register(@RequestBody Account newAccount) {
		return ResponseEntity.ok(this.accountService.register(newAccount));
	}
	
	@PatchMapping
	public ResponseEntity<Integer> update(@RequestBody Account targetAccount) {
		return ResponseEntity.ok(this.accountService.update(targetAccount));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") int id) {
		this.accountService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
}
