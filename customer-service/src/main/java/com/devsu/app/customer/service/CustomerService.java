package com.devsu.app.customer.service;

import java.util.List;

import com.devsu.app.customer.dto.CustomerDto;

public interface CustomerService {

    List<CustomerDto> listAllCustomers();

    CustomerDto findCustomerById(int id);

    CustomerDto findCustomerByPersonalId(String personalId);

    CustomerDto findCustomerByCustomerId(String customerId);

    int register(CustomerDto newCustomer);

    int update(CustomerDto existingCustomer);

    void delete(int id);

}
