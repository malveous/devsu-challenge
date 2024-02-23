package com.devsu.app.customer.model;

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
	
}
