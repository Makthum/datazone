package com.jpr.app.web.rest;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.jpr.app.repository.DimDateRepository;
import com.jpr.app.security.AuthoritiesConstants;
import com.jpr.app.service.ComponentService;
import com.jpr.app.service.HeatService;
import com.jpr.app.service.IssueService;

@RestController
@RequestMapping("/api/graphs")
public class GraphResource {

	@Autowired
	DimDateRepository dimDateRepo;
	@Autowired
	HeatService heatService;

	@Autowired
	IssueService issueService;

	@Autowired
	ComponentService compoService;

	@GetMapping("/monthly")
	@Timed
	@Secured(AuthoritiesConstants.USER)
	public ResponseEntity<String> getHeat(@RequestParam(value="fields") List<String> myParam) throws ParseException, JSONException {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -30);
		Date fromDate = cal.getTime();
		List<Map<Date, Double> >result = heatService.getProduction(fromDate, new Date(),myParam.toArray(new String[myParam.size()]));
		JSONObject object = heatService.getGraphData(result, myParam.toArray(new String[myParam.size()]));
		return new ResponseEntity<>(object.toString(), HttpStatus.OK);
	}

}
