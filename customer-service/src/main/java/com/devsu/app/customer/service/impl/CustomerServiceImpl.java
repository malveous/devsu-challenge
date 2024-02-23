package com.devsu.app.customer.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.devsu.app.customer.exceptions.CustomerNotFoundException;
import com.devsu.app.customer.exceptions.CustomerRequiredDataException;
import com.devsu.app.customer.exceptions.CustomerUpdateException;
import com.devsu.app.customer.model.Customer;
import com.devsu.app.customer.persistence.CustomerRepository;
import com.devsu.app.customer.service.CustomerService;
import com.devsu.app.customer.util.CustomerDataUtility;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

	private final CustomerRepository customerRepository;
	
	@Override
	public List<Customer> listAllCustomers() {
		var customers = (List<Customer>) this.customerRepository.findAll();
		
		if (CollectionUtils.isEmpty(customers)) {
			throw new CustomerNotFoundException("No customers were available for listing");
		}
		
		return customers;
	}

	@Override
	public Customer findCustomerById(int id) {
		var customer = this.customerRepository.findById(id);
		
		if (customer.isEmpty()) {
			throw new CustomerNotFoundException("Could not find a customer with ID: " + id);
		}
		
		return customer.get();
	}

	@Override
	public Customer findCustomerByPersonalId(String personalId) {
		var customer = this.customerRepository.findByPersonalId(personalId);
		
		if (customer.isEmpty()) {
			throw new CustomerNotFoundException("Could not find a customer with personal ID: " + personalId);
		}
		
		return customer.get();
	}
	
	@Override
	public Customer findCustomerByCustomerId(String customerId) {
		var customer = this.customerRepository.findByCustomerId(customerId);
		
		if (customer.isEmpty()) {
			throw new CustomerNotFoundException("Could not find a customer with customer ID: " + customerId);
		}
		
		return customer.get();
	}

	@Override
	public int register(Customer newCustomer) {
		int generatedId = -1;
		
		var personalId = newCustomer.getPersonalId(); // Assuming this is something like the national identity card, should be provided and not generated
		var customerName = newCustomer.getName();
		var customerPassword = newCustomer.getPassword();
		var customerAddress = newCustomer.getAddress();
		var customerPhone = newCustomer.getPhoneNumber();
		var customerAge = newCustomer.getAge();
		var customerGender = newCustomer.getGender();
		
		if (StringUtils.isAnyBlank(personalId, customerName, customerPassword, customerAddress, customerPhone, customerGender) 
				|| customerAge < Customer.MIN_CUSTOMER_REGISTRATION_REQ_AGE) {
			throw new CustomerRequiredDataException("Required values for customer registration are not present in the request or are invalid.");
		} else {
			newCustomer.setCustomerId(CustomerDataUtility.generateCustomerId()); // Generating the customer ID
			newCustomer.setStatus(Boolean.TRUE); // A client being registered can be active as default
			var savedCustomer = this.customerRepository.save(newCustomer);
			generatedId = savedCustomer.getId();
		}
		
		if (generatedId <= 0) {
			throw new CustomerUpdateException("An error occurred while trying to register a new customer.");
		}
		
		return generatedId;
	}

	@Override
	public int update(Customer existingCustomer) {
		int affectedRow = -1;
		
		var customerFound = this.customerRepository.findCustomerBySpecificIdsIfExist(existingCustomer.getCustomerId(), existingCustomer.getPersonalId());
		
		if (customerFound.isPresent()) {
			var customerData = customerFound.get();
			var id = customerData.getId();
			var customerId = customerData.getCustomerId();
			
			existingCustomer.setId(id);
			
			if (StringUtils.isBlank(existingCustomer.getCustomerId())) {
				existingCustomer.setCustomerId(customerId);
			}
			
			this.customerRepository.save(existingCustomer);
			affectedRow = id;
		} else {
			throw new CustomerNotFoundException("There is no customer data available for update");
		}
		
		if (affectedRow <= 0) {
			throw new CustomerUpdateException("An error occurred while trying to update customer information.");
		}
		
		return affectedRow;
	}

	@Override
	public void delete(int id) {
		try {
			this.customerRepository.deleteById(id);
		} catch (Exception e) {
			throw new CustomerUpdateException("An exception occurred while trying to delete a customer.", e);
		}
	}
	
}
