package com.jpr.app.web.rest;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.jpr.app.domain.DimIssue;
import com.jpr.app.security.AuthoritiesConstants;
import com.jpr.app.service.CSVService;
import com.jpr.app.service.IssueService;
import com.jpr.app.service.ReportService;
import com.jpr.app.service.ScrapService;
import com.jpr.app.service.dto.CompositionCost;
import com.jpr.app.service.dto.DailyReportDTO;
import com.jpr.app.service.dto.MonthlyScrapReportDTO;
import com.jpr.app.service.dto.RcLogDTO;

@RestController
@RequestMapping("/api")
public class ReportResource {

	@Autowired
	ReportService reportService;

	@Autowired
	IssueService issueService;

	@Autowired
	ScrapService scrapService;
	@Autowired
	CSVService csvService;

	@PostMapping("/reports/daily")
	@Timed
	@Secured(AuthoritiesConstants.USER)
	public ResponseEntity<DailyReportDTO> createHeatDetails(@RequestBody Date date) {
		DailyReportDTO result = reportService.getDailyReport(date);
		if (result == null)
			throw new RuntimeErrorException(null, "No Records Found for the request date");
		return new ResponseEntity<>(result, HttpStatus.OK);

	}

	@PostMapping("/reports/daily/composition")
	@Timed
	@Secured(AuthoritiesConstants.USER)
	public ResponseEntity<Map<String, Double>> getComposition(@RequestBody Date date) {
		Map<String, Double> result = scrapService.getComposition(date);
		if (result == null)
			throw new RuntimeErrorException(null, "No Records Found for the request date");
		return new ResponseEntity<>(result, HttpStatus.OK);

	}

	@PostMapping("/reports/daily/cost")
	@Timed
	@Secured(AuthoritiesConstants.USER)
	public ResponseEntity<Map<String, Double>> getCost(@RequestBody Date date) {
		List<CompositionCost> temp = reportService.getCostComposition(date);
		Map<String, Double> result = new HashMap<>();
		double totalQuantity = 0;
		double totalvalue = 0;
		for (CompositionCost cost : temp) {
			totalQuantity += cost.getQuantity();
			totalvalue += (cost.getPrice() * cost.getQuantity());
			result.put(cost.getScrapName(), cost.getPrice() * cost.getQuantity());
		}
		result.put("Average", totalvalue / totalQuantity);
		return new ResponseEntity<>(result, HttpStatus.OK);

	}

	@PostMapping("/reports/issues")
	@Timed
	@Secured(AuthoritiesConstants.USER)
	public ResponseEntity<List<DimIssue>> getIssues(@RequestBody RcLogDTO dto) {
		List<DimIssue> issues = issueService.getIssues(dto.getFromDate(), dto.getToDate(), dto.getPage(),
				dto.getSize());
		return new ResponseEntity<>(issues, HttpStatus.OK);

	}

	@PostMapping("/issues/reportdownload")
	@Timed
	@Secured(AuthoritiesConstants.USER)
	public void downloadHeatDetails(@RequestBody RcLogDTO dto, HttpServletResponse response) throws IOException {
		List<DimIssue> issues = issueService.getIssues(dto.getFromDate(), dto.getToDate(), dto.getPage(),
				dto.getSize());
		csvService.writeAll(response.getWriter(), issues, DimIssue.class);
	}

	@PostMapping("/reports/monthly/received")
	@Timed
	@Secured(AuthoritiesConstants.USER)
	public ResponseEntity<List<MonthlyScrapReportDTO>> monthlyReceivedReport(@RequestBody RcLogDTO dto) {
		List<MonthlyScrapReportDTO> result = reportService.getMonthlyReceivedReport(dto.getFromDate(), dto.getToDate(),
				dto.getPage(), dto.getSize());
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/reports/monthly/issued")
	@Timed
	@Secured(AuthoritiesConstants.USER)
	public ResponseEntity<List<MonthlyScrapReportDTO>> monthlyIssuedReport(@RequestBody RcLogDTO dto) {
		List<MonthlyScrapReportDTO> result = reportService.getMonthlyIssuedReport(dto.getFromDate(), dto.getToDate(),
				dto.getPage(), dto.getSize());
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

}
