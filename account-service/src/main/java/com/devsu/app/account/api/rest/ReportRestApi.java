package com.devsu.app.account.api.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devsu.app.account.model.AccountRecordReport;
import com.devsu.app.account.service.ReportService;

@RestController
@RequestMapping("/api/report")
public class ReportRestApi {

	@Autowired
	private ReportService reportService;
	
	@GetMapping
	public List<AccountRecordReport> generateAccountRecordReport(@RequestParam String start,
			@RequestParam String end, @RequestParam String personalId) {
		return this.reportService.generateAccountRecordReport(start, end, personalId);
	}
	
}
