package com.devsu.app.account.model;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccountRecordReport {

	private Instant date;
	private String customerName;
	private String accountNumber;
	private String accountType;
	private double initialBalance;
	private String status;
	private double operationValue;
	private double availableBalance;
	
}
