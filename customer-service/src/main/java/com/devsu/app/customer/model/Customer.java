package com.devsu.app.customer.model;

import com.devsu.app.customer.dto.CustomerDto;
import com.devsu.app.customer.enums.CustomerStatus;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class Customer extends Person {

    public static final int MIN_CUSTOMER_REGISTRATION_REQ_AGE = 18;

    private String customerId;
    private String password;
    private boolean status;

    public Customer(CustomerDto customerInput) {
        super(Person.builder().name(customerInput.getName()).gender(customerInput.getGender().getGenderValue())
                .age(customerInput.getAge()).personalId(customerInput.getPersonalId())
                .address(customerInput.getAddress()).phoneNumber(customerInput.getPhoneNumber()));
        this.customerId = customerInput.getCustomerId();
        this.password = customerInput.getPassword();
        this.status = CustomerStatus.asBoolean(customerInput.getStatus());
    }

}
