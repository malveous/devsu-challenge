package com.devsu.app.customer.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.devsu.app.customer.dto.CustomerDto;
import com.devsu.app.customer.enums.CustomerStatus;
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
    public List<CustomerDto> listAllCustomers() {
        var customersEntityList = (List<Customer>) this.customerRepository.findAll();

        if (CollectionUtils.isEmpty(customersEntityList)) {
            throw new CustomerNotFoundException("No customers were available for listing");
        }

        var customers = new ArrayList<CustomerDto>();

        for (Customer customerEntity : customersEntityList) {
            customers.add(new CustomerDto(customerEntity));
        }

        return customers;
    }

    @Override
    public CustomerDto findCustomerById(int id) {
        var customerEntity = this.customerRepository.findById(id);

        if (customerEntity.isEmpty()) {
            throw new CustomerNotFoundException("Could not find a customer with ID: " + id);
        }

        return new CustomerDto(customerEntity.get());
    }

    @Override
    public CustomerDto findCustomerByPersonalId(String personalId) {
        var customerEntity = this.customerRepository.findByPersonalId(personalId);

        if (customerEntity.isEmpty()) {
            throw new CustomerNotFoundException("Could not find a customer with personal ID: " + personalId);
        }

        return new CustomerDto(customerEntity.get());
    }

    @Override
    public CustomerDto findCustomerByCustomerId(String customerId) {
        var customerEntity = this.customerRepository.findByCustomerId(customerId);

        if (customerEntity.isEmpty()) {
            throw new CustomerNotFoundException("Could not find a customer with customer ID: " + customerId);
        }

        return new CustomerDto(customerEntity.get());
    }

    @Override
    public int register(CustomerDto newCustomer) {
        int generatedId = -1;

        var personalId = newCustomer.getPersonalId(); // Assuming this is something like the national identity card,
                                                      // should be provided and not generated
        var customerName = newCustomer.getName();
        var customerPassword = newCustomer.getPassword();
        var customerAddress = newCustomer.getAddress();
        var customerPhone = newCustomer.getPhoneNumber();
        var customerGender = newCustomer.getGender();

        if (StringUtils.isAnyBlank(personalId, customerName, customerPassword, customerAddress, customerPhone,
                customerGender.toString())) {
            throw new CustomerRequiredDataException(
                    "Required values for customer registration are not present in the request or are invalid.");
        } else {
            newCustomer.setCustomerId(CustomerDataUtility.generateCustomerId()); // Generating the customer ID
            newCustomer.setStatus(CustomerStatus.ACTIVE); // A client being registered can be active as default
            var newCustomerEntity = new Customer(newCustomer);
            var savedCustomer = this.customerRepository.save(newCustomerEntity);
            generatedId = savedCustomer.getId();
        }

        if (generatedId <= 0) {
            throw new CustomerUpdateException("An error occurred while trying to register a new customer.");
        }

        return generatedId;
    }

    @Override
    public int update(CustomerDto existingCustomer) {
        int affectedRow = -1;

        var customerEntityFound = this.customerRepository
                .findCustomerBySpecificIdsIfExist(existingCustomer.getCustomerId(), existingCustomer.getPersonalId());

        if (customerEntityFound.isPresent()) {
            var customerEntityData = customerEntityFound.get();
            var id = customerEntityData.getId();
            var customerId = customerEntityData.getCustomerId();

            existingCustomer.setId(id);

            if (StringUtils.isBlank(existingCustomer.getCustomerId())) {
                existingCustomer.setCustomerId(customerId);
            }

            this.customerRepository.save(customerEntityData);
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
