package com.jpr.app.web.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.jpr.app.domain.DimComponent;
import com.jpr.app.domain.DimHeat;
import com.jpr.app.domain.DimIssue;
import com.jpr.app.security.AuthoritiesConstants;
import com.jpr.app.service.ComponentService;
import com.jpr.app.service.IssueService;
import com.jpr.app.service.ScrapService;
import com.jpr.app.service.dto.IssueDTO;
import com.jpr.app.service.dto.RcLogDTO;

@RestController
@RequestMapping("/api")
public class IssueResource {

	@Autowired
	IssueService issueService;

	@Autowired
	ComponentService compService;

	@Autowired
	ScrapService scrapService;

	@PostMapping("/issues")
	@Timed
	@Secured(AuthoritiesConstants.USER)
	public ResponseEntity<DimIssue> createIssue(@RequestBody IssueDTO details) {
		DimComponent comp = compService.getComponent(details.getComponentId());
		DimIssue temp = new DimIssue();
		temp.setComponent(comp);
		temp.setDimDate(scrapService.getDate(details.getDate()));
		temp.setIssueDescription(details.getDescription());
		temp.setIssueResolution(details.getSolution());
		temp.setWorkedBy(details.getWorkedBy());
		temp.setTimeTaken(details.getTimeTaken());
		DimIssue result = issueService.createDimIssue(temp);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/issues/history")
	@Timed
	@Secured(AuthoritiesConstants.USER)
	public ResponseEntity<List<DimIssue>> getIssues(RcLogDTO details) {
		List<DimIssue> result = issueService.getIssues(details.getFromDate(), details.getToDate(), details.getPage(),
				details.getSize());
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/issues")
	@Timed
	@Secured(AuthoritiesConstants.USER)
	public ResponseEntity<List<DimIssue>> getIssuesLast3days() {
		List<DimIssue> result = issueService.getIssuesLastThreeDays();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

}
