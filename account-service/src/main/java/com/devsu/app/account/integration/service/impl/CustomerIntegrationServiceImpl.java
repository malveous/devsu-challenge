package com.devsu.app.account.integration.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.devsu.app.account.exceptions.AccountNotFoundException;
import com.devsu.app.account.integration.model.Customer;
import com.devsu.app.account.integration.service.CustomerIntegrationService;
import com.devsu.app.account.repository.AccountRepository;

@Service
public class CustomerIntegrationServiceImpl implements CustomerIntegrationService {

	@Value("${customer-api.integration.url}")
	private String customerApiIntegrationUrl;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Override
	public Customer getCustomerDataByPersonalId(String personalId) {
		Customer customerData = null;
		var restTemplate = new RestTemplate();
		var resourceUrl = String.format("%s/personalId/%s", this.customerApiIntegrationUrl, personalId);
		
		ResponseEntity<Customer> customerResponseEntity = restTemplate.getForEntity(resourceUrl, Customer.class);
		
		if (customerResponseEntity.getStatusCode().is2xxSuccessful() && customerResponseEntity.hasBody()) {
			customerData = customerResponseEntity.getBody();
		}
		return customerData;
	}
	
	@Override
	public Customer getCustomerDataByAccountNumber(String accountNumber) throws AccountNotFoundException {
		Customer customerData = null;
		var accountFound = this.accountRepository.findByAccountNumber(accountNumber);
		
		if (accountFound.isEmpty()) {
			throw new AccountNotFoundException("No account was found for the specified number");
		} else {
			var accountData = accountFound.get();
			var personalId = accountData.getPersonalId();
			
			var restTemplate = new RestTemplate();
			var resourceUrl = String.format("%s%s", this.customerApiIntegrationUrl, personalId);
			
			ResponseEntity<Customer> customerResponseEntity = restTemplate.getForEntity(resourceUrl, Customer.class);
			
			if (customerResponseEntity.getStatusCode().is2xxSuccessful() && customerResponseEntity.hasBody()) {
				customerData = customerResponseEntity.getBody();
			}
			
		}
		return customerData;
	}
	
}
