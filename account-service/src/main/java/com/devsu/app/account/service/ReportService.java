package com.devsu.app.account.service;

import java.util.List;

import com.devsu.app.account.model.AccountRecordReport;

public interface ReportService {

	List<AccountRecordReport> generateAccountRecordReport(String start, String end, String personalId);
	
}
