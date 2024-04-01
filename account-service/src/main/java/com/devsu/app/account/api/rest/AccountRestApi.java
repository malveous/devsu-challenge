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

import com.devsu.app.account.dto.AccountDto;
import com.devsu.app.account.service.AccountService;

@RestController
@RequestMapping("/api/account")
public class AccountRestApi {

    @Autowired
    private AccountService accountService;

    @GetMapping
    public List<AccountDto> listAllAccounts() {
        return this.accountService.listAllAccounts();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<AccountDto> findAccountById(@PathVariable(value = "id") int id) {
        return ResponseEntity.ok(this.accountService.findAccountById(id));
    }

    @GetMapping("/accountNumber/{accountNumber}")
    public ResponseEntity<AccountDto> findAccountByAccountNumber(
            @PathVariable(value = "accountNumber") String accountNumber) {
        return ResponseEntity.ok(this.accountService.findAccountByAccountNumber(accountNumber));
    }

    @PostMapping("/newAccount")
    public ResponseEntity<Integer> register(@RequestBody AccountDto newAccount) {
        return ResponseEntity.ok(this.accountService.register(newAccount));
    }

    @PatchMapping
    public ResponseEntity<Integer> update(@RequestBody AccountDto targetAccount) {
        return ResponseEntity.ok(this.accountService.update(targetAccount));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") int id) {
        this.accountService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
