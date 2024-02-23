package com.devsu.app.customer.service;

import java.util.List;

import com.devsu.app.customer.model.Customer;

public interface CustomerService {

	List<Customer> listAllCustomers();
	
	Customer findCustomerById(int id);
	
	Customer findCustomerByPersonalId(String personalId);
	
	Customer findCustomerByCustomerId(String customerId);
	
	int register(Customer newCustomer);
	
	int update(Customer existingCustomer);
	
	void delete(int id);
	
}
