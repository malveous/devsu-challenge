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

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
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
        log.info("Calling {} for retrieving customer data using ID {}", resourceUrl, personalId);

        ResponseEntity<Customer> customerResponseEntity = restTemplate.getForEntity(resourceUrl, Customer.class);

        if (customerResponseEntity.getStatusCode().is2xxSuccessful() && customerResponseEntity.hasBody()) {
            customerData = customerResponseEntity.getBody();
            log.debug("Customer Data found: " + customerData);
        } else {
            log.debug("No customer data was found for given personal ID {}", personalId);
        }

        return customerData;
    }

    @Override
    public Customer getCustomerDataByAccountNumber(String accountNumber) throws AccountNotFoundException {
        Customer customerData = null;
        log.debug("Performing customer data search by account number: {}", accountNumber);
        var accountFound = this.accountRepository.findByAccountNumber(accountNumber);

        if (accountFound.isEmpty()) {
            log.debug("{}: {}", AccountNotFoundException.DEFAULT_MSG, accountNumber);
            throw new AccountNotFoundException();
        } else {
            var accountData = accountFound.get();
            var personalId = accountData.getPersonalId();

            var restTemplate = new RestTemplate();
            var resourceUrl = String.format("%s%s", this.customerApiIntegrationUrl, personalId);
            log.info("Calling {} for retrieving customer data using ID {}", resourceUrl, personalId);

            ResponseEntity<Customer> customerResponseEntity = restTemplate.getForEntity(resourceUrl, Customer.class);

            if (customerResponseEntity.getStatusCode().is2xxSuccessful() && customerResponseEntity.hasBody()) {
                customerData = customerResponseEntity.getBody();
                log.debug("Customer Data found: " + customerData);
            } else {
                log.debug("No customer data was found for given personal ID {}", personalId);
            }

        }
        return customerData;
    }

}
