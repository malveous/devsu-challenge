package com.devsu.app.account.integration.service;

import com.devsu.app.account.integration.model.Customer;

public interface CustomerIntegrationService {

    Customer getCustomerDataByPersonalId(String personalId);

    Customer getCustomerDataByAccountNumber(String accountNumber);
}
