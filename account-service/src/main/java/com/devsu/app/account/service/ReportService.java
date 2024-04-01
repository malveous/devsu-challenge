package com.devsu.app.account.service;

import java.util.List;

import com.devsu.app.account.dto.AccountRecordReportDto;

public interface ReportService {

    List<AccountRecordReportDto> generateAccountRecordReport(String start, String end, String personalId);

}
