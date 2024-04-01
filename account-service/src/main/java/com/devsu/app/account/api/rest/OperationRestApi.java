package com.devsu.app.account.api.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsu.app.account.dto.OperationDto;
import com.devsu.app.account.service.OperationService;

@RestController
@RequestMapping("/api/operation")
public class OperationRestApi {

    @Autowired
    private OperationService operationService;

    @GetMapping
    public List<OperationDto> listAllOperations() {
        return this.operationService.listAllOperations();
    }

    @GetMapping(path = "/accountNumber/{accountNumber}")
    public List<OperationDto> listOperationsByAccountNumber(
            @PathVariable(value = "accountNumber") String accountNumber) {
        return this.operationService.listOperationsByAccountNumber(accountNumber);
    }

    @GetMapping(path = "/id/{id}")
    public ResponseEntity<OperationDto> findOperationById(@PathVariable(value = "id") int id) {
        return ResponseEntity.ok(this.operationService.findOperationById(id));
    }

    @PostMapping("/accountId/{accountId}")
    public ResponseEntity<Integer> registerOperationUsingAccountId(@PathVariable(value = "accountId") int accountId,
            @RequestBody OperationDto newOperation) {
        newOperation.setAccountId(accountId);

        var operationId = this.operationService.register(newOperation);
        return ResponseEntity.ok(operationId);
    }

    @PostMapping("/accountNumber/{accountNumber}")
    public ResponseEntity<Integer> registerOperationUsingAccountNumber(
            @PathVariable(value = "accountNumber") String accountNumber, @RequestBody OperationDto newOperation) {
        newOperation.setAccountNumber(accountNumber);
        var operationId = this.operationService.register(newOperation);
        return ResponseEntity.ok(operationId);
    }

}
