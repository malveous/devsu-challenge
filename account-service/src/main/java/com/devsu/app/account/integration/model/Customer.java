package com.devsu.app.account.integration.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Customer {

	private int id;
	private String name;
	private String gender; // This can be handled with either an enum or a database table
	private int age;
	private String personalId;
	private String address;
	private String phoneNumber;
	private String customerId;
	private String password;
	private boolean status;
	
}
