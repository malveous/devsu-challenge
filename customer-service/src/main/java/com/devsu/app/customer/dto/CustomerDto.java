package com.devsu.app.customer.dto;

import com.devsu.app.customer.enums.CustomerStatus;
import com.devsu.app.customer.enums.Gender;
import com.devsu.app.customer.model.Customer;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CustomerDto {

    private int id;

    @NotBlank
    private String name;

    private Gender gender;
    @Min(Customer.MIN_CUSTOMER_REGISTRATION_REQ_AGE)
    private int age;

    @NotBlank
    private String personalId;
    private String address;
    private String phoneNumber;
    private String customerId;
    @NotBlank
    @Size(min = 8, max = 12, message = "Password should have 8 characters length as minimum and 12 characters as maximum")
    private String password;
    private CustomerStatus status;

    public CustomerDto(Customer customer) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.gender = Gender.getGenderFromValueString(customer.getGender());
        this.age = customer.getAge();
        this.personalId = customer.getPersonalId();
        this.address = customer.getAddress();
        this.phoneNumber = customer.getPhoneNumber();
        this.customerId = customer.getCustomerId();
        this.password = customer.getPassword();
        this.status = customer.isStatus() ? CustomerStatus.ACTIVE : CustomerStatus.INACTIVE;
    }

}
