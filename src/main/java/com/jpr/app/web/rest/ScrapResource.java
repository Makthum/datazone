package com.jpr.app.web.rest;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpr.app.domain.DimScrap;
import com.jpr.app.domain.FactScrapIssued;
import com.jpr.app.domain.FactScrapRecv;
import com.jpr.app.domain.User;
import com.jpr.app.security.AuthoritiesConstants;
import com.jpr.app.service.CSVService;
import com.jpr.app.service.ScrapService;
import com.jpr.app.service.dto.MonthlyOCBalanceDTO;
import com.jpr.app.service.dto.RcLogDTO;
import com.jpr.app.service.dto.ScrapReceivedDTO;
import com.jpr.app.service.dto.UserDTO;

@RestController
@RequestMapping("/api")
public class ScrapResource {

	private final Logger log = LoggerFactory.getLogger(UserResource.class);

	@Autowired
	private ScrapService scrapService;

	@Autowired
	private CSVService csvService;

	@PostMapping("/scrap/type")
	@Timed
	@Secured(AuthoritiesConstants.USER)
	public ResponseEntity<List<DimScrap>> createScrap(@RequestBody List<DimScrap> details) {
		List<DimScrap> result = scrapService.saveScrapType(details);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/scrap/getid")
	@Timed
	@Secured(AuthoritiesConstants.USER)
	public ResponseEntity<Integer> getId() {
		return new ResponseEntity<>(scrapService.getScrapMaxId(), HttpStatus.OK);
	}

	@GetMapping("/scrap/types")
	@Timed
	@Secured(AuthoritiesConstants.USER)
	public ResponseEntity<List<DimScrap>> getTypes() {
		return new ResponseEntity<>(scrapService.getScrapTypes(), HttpStatus.OK);
	}

	@RequestMapping(value = "/scrap/types", method = RequestMethod.PUT, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@Secured(AuthoritiesConstants.USER)
	public ResponseEntity<String> UpdateScrapType(@RequestBody DimScrap detail) {
		DimScrap result = scrapService.getDimScrap(detail.getId());
		if (result == null)
			return new ResponseEntity<>("Invalid Id", HttpStatus.OK);
		else
			scrapService.saveScrapType(detail);
		return new ResponseEntity<>("Updated Successfully", HttpStatus.OK);
	}

	@RequestMapping(value = "/scrap/received", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@Secured(AuthoritiesConstants.USER)
	public ResponseEntity<String> scrapReceived(@RequestBody List<ScrapReceivedDTO> details) {
		List<FactScrapRecv> result = scrapService.saveScrapReceived(details);
		if (result == null)
			return new ResponseEntity<>("Invalid Id", HttpStatus.OK);
		return new ResponseEntity<>("Updated Successfully", HttpStatus.OK);
	}

	@RequestMapping(value = "/scrap/receivedlogs", method = RequestMethod.POST)
	@Timed
	@Secured(AuthoritiesConstants.USER)
	public ResponseEntity<List<FactScrapRecv>> scrapReceivedLogs(@RequestBody RcLogDTO detail) {
		List<FactScrapRecv> result = scrapService.getScrapReceivedLogs(detail.getFromDate(), detail.getToDate(),
				detail.getPage(), detail.getSize());
		if (result == null)
			throw new RuntimeException("Invalid Date");
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@RequestMapping(value = "/scrap/receivedreport", method = RequestMethod.POST)
	@Timed
	@Secured(AuthoritiesConstants.USER)
	public ResponseEntity<String> getScrapReceivedReport(@RequestBody RcLogDTO detail) throws JsonProcessingException {
		List<Map<String, Object>> result = scrapService.getScrapReceivedReport(detail.getFromDate(), detail.getToDate(),
				detail.getPage(), detail.getSize());
		ObjectMapper mapper = new ObjectMapper();
		String resultstr = mapper.writeValueAsString(result);
		return new ResponseEntity<>(resultstr, HttpStatus.OK);
	}

	@RequestMapping(value = "/scrap/receivedreportdownload", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@Secured(AuthoritiesConstants.USER)
	public void getScrapReceivedReport(@RequestBody RcLogDTO detail, HttpServletResponse response) throws IOException {
		List<Map<String, Object>> result = scrapService.getScrapReceivedReport(detail.getFromDate(), detail.getToDate(),
				detail.getPage(), detail.getSize());
		csvService.writeAll(result, response.getWriter());
	}

	@RequestMapping(value = "/scrap/issued", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@Secured(AuthoritiesConstants.USER)
	public ResponseEntity<String> scrapIssued(@RequestBody List<ScrapReceivedDTO> details) {
		List<FactScrapIssued> result = scrapService.saveScrapIssued(details);
		if (result == null)
			return new ResponseEntity<>("Invalid Id", HttpStatus.OK);
		return new ResponseEntity<>("Updated Successfully", HttpStatus.OK);
	}

	@RequestMapping(value = "/scrap/issuedreport", method = RequestMethod.POST)
	@Timed
	@Secured(AuthoritiesConstants.USER)
	public ResponseEntity<String> getScrapIssuedReport(@RequestBody RcLogDTO detail) throws JsonProcessingException {
		List<Map<String, Object>> result = scrapService.getScrapIssuedReport(detail.getFromDate(), detail.getToDate(),
				detail.getPage(), detail.getSize());
		ObjectMapper mapper = new ObjectMapper();
		String resultstr = mapper.writeValueAsString(result);
		return new ResponseEntity<>(resultstr, HttpStatus.OK);
	}

	@RequestMapping(value = "/scrap/issuedreportdownload", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@Secured(AuthoritiesConstants.USER)
	public void downloadScrapIssuedReport(@RequestBody RcLogDTO detail, HttpServletResponse response)
			throws IOException {
		List<Map<String, Object>> result = scrapService.getScrapIssuedReport(detail.getFromDate(), detail.getToDate(),
				detail.getPage(), detail.getSize());
		csvService.writeAll(result, response.getWriter());
	}

	@RequestMapping(value = "/scrap/monthlybalance", method = RequestMethod.POST)
	@Timed
	@Secured(AuthoritiesConstants.USER)
	public ResponseEntity<List<MonthlyOCBalanceDTO>> getMonthlyBalance(@RequestBody RcLogDTO detail,
			HttpServletResponse response) throws IOException {
		List<MonthlyOCBalanceDTO> result = scrapService.getMonthlyBalance(detail.getFromDate(), detail.getToDate());
		return new ResponseEntity<List<MonthlyOCBalanceDTO>>(result, HttpStatus.OK);
	}

	@RequestMapping(value = "/scrap/monthlybalancedownload", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@Timed
	@Secured(AuthoritiesConstants.USER)
	public void downloadSMonthlyBalance(@RequestBody RcLogDTO detail, HttpServletResponse response)
			throws IOException {
		List<MonthlyOCBalanceDTO> result = scrapService.getMonthlyBalance(detail.getFromDate(), detail.getToDate());
		csvService.writeAll(response.getWriter(),result, MonthlyOCBalanceDTO.class);
	}

}
