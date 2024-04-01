package com.devsu.app.account.service;

import java.util.List;

import com.devsu.app.account.dto.OperationDto;

public interface OperationService {

    List<OperationDto> listAllOperations();

    List<OperationDto> listOperationsByAccountNumber(String accountNumber);

    OperationDto findOperationById(int id);

    int register(OperationDto newOperation);

    int update(OperationDto existingOperation);

    void delete(int id);

}
