package com.jpr.app.web.rest;

import java.util.List;
import java.util.concurrent.TimeUnit;

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
import com.jpr.app.domain.DimDate;
import com.jpr.app.domain.DimHeat;
import com.jpr.app.domain.DimIssue;
import com.jpr.app.domain.FactHeatDetails;
import com.jpr.app.domain.FactHeatMixture;
import com.jpr.app.domain.FactHeatRawMaterialMixture;
import com.jpr.app.domain.HeatPk;
import com.jpr.app.repository.DimDateRepository;
import com.jpr.app.security.AuthoritiesConstants;
import com.jpr.app.service.ComponentService;
import com.jpr.app.service.HeatService;
import com.jpr.app.service.IssueService;
import com.jpr.app.service.dto.DimHeatDTO;
import com.jpr.app.service.dto.HeatDetailsDTO;
import com.jpr.app.service.dto.HeatRawMaterialMixtureDTO;
import com.jpr.app.service.dto.RcLogDTO;

@RestController
@RequestMapping("/api")
public class HeatResource {

	@Autowired
	DimDateRepository dimDateRepo;
	@Autowired
	HeatService heatService;

	@Autowired
	IssueService issueService;

	@Autowired
	ComponentService compoService;

	@PostMapping("/heats")
	@Timed
	@Secured(AuthoritiesConstants.USER)
	public ResponseEntity<DimHeat> createHeat(@RequestBody DimHeatDTO details) {
		DimDate date = dimDateRepo.findByDate(details.getDate());
		DimHeat temp = new DimHeat();
		temp.setDimDate(date);
		temp.setFurnaceId(details.getFurnaceId());
		temp.setHeatId(details.getHeatId());
		temp.setSinteringHeatId(details.getSinteringHeatId());
		DimHeat result = heatService.createDimHeat(temp);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/heats")
	@Timed
	@Secured(AuthoritiesConstants.USER)
	public ResponseEntity<List<DimHeat>> getHeat() {
		List<DimHeat> result = heatService.getHeats();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/heats/recent")
	@Timed
	@Secured(AuthoritiesConstants.USER)
	public ResponseEntity<List<DimHeat>> getRecentHeat() {
		List<DimHeat> result = heatService.getRecentHeats();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/heatdetails")
	@Timed
	@Secured(AuthoritiesConstants.USER)
	public ResponseEntity<FactHeatDetails> createHeatDetails(@RequestBody HeatDetailsDTO details) {
		FactHeatDetails result = heatService.createHeatDetails(details);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/heatdetails/report")
	@Timed
	@Secured(AuthoritiesConstants.USER)
	public ResponseEntity<List<FactHeatDetails>> getHeatDetails(@RequestBody RcLogDTO details) {
		List<FactHeatDetails> result = heatService.getHeatDetails(details.getFromDate(), details.getToDate(),
				details.getPage(), details.getSize());
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/heatdetails/heatmix")
	@Timed
	@Secured(AuthoritiesConstants.USER)
	public ResponseEntity<FactHeatMixture> saveHeatMix(@RequestBody FactHeatMixture details) {
		FactHeatMixture result = heatService.createHeatMixture(details);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/heatdetails/rawmaterialmix")
	@Timed
	@Secured(AuthoritiesConstants.USER)
	public ResponseEntity<FactHeatRawMaterialMixture> saveRawMatMix(@RequestBody HeatRawMaterialMixtureDTO details) {
		FactHeatRawMaterialMixture result = heatService.createRawMaerialMix(details);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

}
