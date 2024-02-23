package com.devsu.app.account.service.impl;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devsu.app.account.exceptions.InvalidParametersForReportException;
import com.devsu.app.account.integration.service.impl.CustomerIntegrationServiceImpl;
import com.devsu.app.account.model.AccountRecordReport;
import com.devsu.app.account.repository.AccountRepository;
import com.devsu.app.account.repository.OperationRepository;
import com.devsu.app.account.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private OperationRepository operationRepository;
	
	@Autowired
	private CustomerIntegrationServiceImpl customerIntegrationService;
	
	@Override
	public List<AccountRecordReport> generateAccountRecordReport(String start, String end, String personalId) {
		List<AccountRecordReport> accountRecordReports = new ArrayList<>();
		
		if (StringUtils.isBlank(personalId)) {
			throw new InvalidParametersForReportException("Customer personal ID is required for report generation");
		}
		
		if (StringUtils.isAnyBlank(start, end)) {
			throw new InvalidParametersForReportException("Start and end dates are required for report generation");
		}
		
		Instant startDate = null; 
		Instant endDate = null;
		
		try {
			startDate = LocalDate.parse(start).atStartOfDay().toInstant(ZoneOffset.UTC);
			endDate = LocalDate.parse(end).atStartOfDay().toInstant(ZoneOffset.UTC);
		} catch (Exception e) {
			throw new InvalidParametersForReportException("The date format specified for date range is not valid", e);
		}
		
		var account = this.accountRepository.findByPersonalId(personalId);
		
		if (account.isPresent()) {
			var accountData = account.get();
			var operations = this.operationRepository.retrieveOperationsByAccountIdAndDateRange(accountData.getId(), startDate, endDate);
			var customer = this.customerIntegrationService.getCustomerDataByPersonalId(personalId);
			
			for (var operation : operations) {
				accountRecordReports.add(new AccountRecordReport(operation.getDate(), customer.getName(), accountData.getAccountNumber(), 
						accountData.getAccountType(), accountData.getInitialBalance(), accountData.isStatus() ? "ACTIVE" : "INACTIVE", 
								operation.getValue(), operation.getBalance()));
			}
			
		}
				
		return accountRecordReports;
	}

	
	
}
