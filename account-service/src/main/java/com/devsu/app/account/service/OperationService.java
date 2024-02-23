package com.devsu.app.account.service;

import java.util.List;

import com.devsu.app.account.model.Operation;

public interface OperationService {

	List<Operation> listAllOperations();
	
	List<Operation> listOperationsByAccountNumber(String accountNumber);
	
	Operation findOperationById(int id);
	
	int register(Operation newOperation);
	
	int update(Operation existingOperation);
	
	void delete(int id);
	
}
