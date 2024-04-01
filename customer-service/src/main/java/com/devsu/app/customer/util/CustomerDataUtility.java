package com.devsu.app.customer.util;

import java.util.UUID;

import lombok.experimental.UtilityClass;

@UtilityClass

public class CustomerDataUtility {

    public String generateCustomerId() {
        return UUID.randomUUID().toString(); // This could vary depending on business requirements
    }

}
