package com.devsu.app.account.service.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.devsu.app.account.dto.OperationDto;
import com.devsu.app.account.exceptions.OperationNotFoundException;
import com.devsu.app.account.exceptions.OperationUpdateException;
import com.devsu.app.account.exceptions.OutOfBalanceException;
import com.devsu.app.account.model.Account;
import com.devsu.app.account.model.Operation;
import com.devsu.app.account.repository.AccountRepository;
import com.devsu.app.account.repository.OperationRepository;
import com.devsu.app.account.service.OperationService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OperationServiceImpl implements OperationService {

    private final OperationRepository operationRepository;
    private final AccountRepository accountRepository;

    @Override
    public List<OperationDto> listAllOperations() {
        log.info("Performing operations listing");
        var operations = this.operationRepository.findAll();

        if (CollectionUtils.isEmpty(operations)) {
            throw new OperationNotFoundException("No operations were available for listing");
        }

        var operationsDtoList = new ArrayList<OperationDto>();
        for (var operationEntity : operations) {
            operationsDtoList.add(new OperationDto(operationEntity));
        }

        log.info("# of operations found: {}", operationsDtoList.size());
        log.debug("Operations details: {}", operationsDtoList);

        return operationsDtoList;
    }

    @Override
    public List<OperationDto> listOperationsByAccountNumber(String accountNumber) {
        log.info("Performing operations search by account number with value: {}", accountNumber);
        var operationsByAccountNumber = this.operationRepository.findOperationsByAccountNumber(accountNumber);

        if (CollectionUtils.isEmpty(operationsByAccountNumber)) {
            throw new OperationNotFoundException("No operations were found for account number: " + accountNumber);
        }
        var operationsDtoList = new ArrayList<OperationDto>();
        for (var operationEntity : operationsByAccountNumber) {
            operationsDtoList.add(new OperationDto(operationEntity));
        }

        log.info("# of operations found: {}", operationsByAccountNumber.size());
        log.debug("Operations details: {}", operationsByAccountNumber);

        return operationsDtoList;
    }

    @Override
    public OperationDto findOperationById(int id) {
        log.info("Performing operation search by ID with value {}", id);
        var operationById = this.operationRepository.findById(id);

        if (operationById.isEmpty()) {
            throw new OperationNotFoundException("No operation was found by id: " + id);
        }

        log.debug("Operation details: {}", operationById);

        return new OperationDto(operationById.get());
    }

    @Override
    @Transactional
    public int register(OperationDto newOperation) {
        log.info("Performing operation registration for new operation: {}", newOperation);
        int generatedOperationId = -1;

        var accountId = newOperation.getAccountId();
        var accountNumber = newOperation.getAccountNumber();
        Optional<Account> accountData = Optional.empty();

        if (accountId > 0) {
            accountData = this.accountRepository.findById(accountId);
        } else if (StringUtils.isNotEmpty(accountNumber)) {
            accountData = this.accountRepository.findByAccountNumber(accountNumber);
        } else {
            throw new OperationUpdateException(
                    "An operation requires either an account ID or account number to be registered");
        }

        if (accountData.isEmpty()) {
            throw new OperationUpdateException("The account specified for operation registration is not valid: "
                    + newOperation.getAccountNumber());
        } else {
            // Validating account balance
            var associatedAccount = accountData.get();
            var operationValue = newOperation.getValue();
            var currAccountBalance = associatedAccount.getInitialBalance();

            if (operationValue < 0) {
                // Negative means we are reducing the balance
                var toReduce = Math.abs(operationValue);
                if (toReduce > currAccountBalance) {
                    throw new OutOfBalanceException("Out of balance for this operation");
                } else {
                    if (StringUtils.isBlank(newOperation.getType())) {
                        newOperation.setType(Operation.DEFAULT_REDUCING_BALANCE_OPERATION_TYPE);
                    }
                }
            } else {
                // We are increasing the balance
                if (StringUtils.isBlank(newOperation.getType())) {
                    newOperation.setType(Operation.DEFAULT_ADDING_FUNDS_OPERATION_TYPE);
                }
            }

            newOperation.setDate(Instant.now());
            newOperation.setBalance(currAccountBalance + operationValue);
            var upToDateBalance = newOperation.getBalance();
            // Updating the balance in the account
            associatedAccount.setInitialBalance(upToDateBalance);
            var operationEntity = new Operation(newOperation, accountData.get());
            // Now saving the operation
            var savedOperation = this.operationRepository.save(operationEntity);
            generatedOperationId = savedOperation.getId();
            log.info("Operation has been registered successfully");
        }

        if (generatedOperationId < 0) {
            throw new OperationUpdateException("Cannot register the operation, plesae verify input data");
        }
        log.debug("Operation registration ID: {}", generatedOperationId);
        return generatedOperationId;
    }

    @Override
    public int update(OperationDto existingOperation) {
        // Operations associated to account (bank transactions) cannot be updated, they should be kept in record
        // history.
        throw new UnsupportedOperationException("Operations cannot be modified due to integrity constraints.");
    }

    @Override
    public void delete(int id) {
        // Same for deletion, in any case, probably an update can occur to logical "delete" by updating an operation
        // state to "revoked"
        throw new UnsupportedOperationException("Operations cannot be deleted due to integrity constraints.");
    }

}
