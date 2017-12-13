package com.jpr.app.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.jpr.app.domain.RawMaterialCost;
import com.jpr.app.security.AuthoritiesConstants;
import com.jpr.app.service.CostService;
import com.jpr.app.service.dto.CostDTO;

@RestController
@RequestMapping("/api")
public class CostResource {

	@Autowired
	private CostService costService;

	@PostMapping("/cost")
	@Timed
	@Secured(AuthoritiesConstants.USER)
	public ResponseEntity<RawMaterialCost> createHeatDetails(@RequestBody CostDTO details) {
		RawMaterialCost result = costService.saveCost(details);
		return new ResponseEntity<>(result, HttpStatus.OK);

	}

}
