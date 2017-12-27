package com.jpr.app.web.rest;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.jpr.app.domain.DimDate;
import com.jpr.app.domain.DimHeat;
import com.jpr.app.domain.FactHeatDetails;
import com.jpr.app.domain.FactHeatMixture;
import com.jpr.app.domain.FactHeatRawMaterialMixture;
import com.jpr.app.repository.DimDateRepository;
import com.jpr.app.security.AuthoritiesConstants;
import com.jpr.app.service.CSVService;
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

	@Autowired
	CSVService csvService;

	@PostMapping("/heats")
	@Timed
	@Secured(AuthoritiesConstants.USER)
	public ResponseEntity<DimHeat> createHeat(@RequestBody DimHeatDTO details) {
		DimDate dateOn = dimDateRepo.findByDate(details.getDateOn());
		DimDate dateOff = dimDateRepo.findByDate(details.getDateOff());
		DimHeat temp = new DimHeat();
		temp.setDimDateOn(dateOn);
		temp.setDimDateOff(dateOff);
		temp.setFurnaceId(details.getFurnaceId());
		temp.setHeatId(details.getHeatId());
		temp.setSinteringHeatId(details.getSinteringHeatId());
		DimHeat result = heatService.createDimHeat(temp);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PutMapping("/heats")
	@Timed
	@Secured(AuthoritiesConstants.USER)
	public ResponseEntity<DimHeat> updateHeat(@RequestBody DimHeatDTO details) {
		DimHeat temp = heatService.getHeat(details.getId());
		if (temp == null)
			return null;
		DimDate dateOn = dimDateRepo.findByDate(details.getDateOn());
		DimDate dateOff = dimDateRepo.findByDate(details.getDateOff());
		temp.setDimDateOn(dateOn);
		temp.setDimDateOff(dateOff);
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

	@GetMapping("/heats/{id}")
	@Timed
	@Secured(AuthoritiesConstants.USER)
	public ResponseEntity<DimHeat> getHeat(@PathVariable("id") Integer id) {
		DimHeat result = heatService.getHeat(id);
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

	@GetMapping("/heatdetails")
	@Timed
	@Secured(AuthoritiesConstants.USER)
	public ResponseEntity<HeatDetailsDTO> getHeatDetails(@RequestParam Integer id) {
		HeatDetailsDTO result = heatService.getHeatDetails(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/heatdetails/heatmix")
	@Timed
	@Secured(AuthoritiesConstants.USER)
	public ResponseEntity<FactHeatMixture> getHeatMix(@RequestParam Integer id) {
		FactHeatMixture result = heatService.getHeatMix(id);
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
	
	
	@PostMapping("/heats/report")
	@Timed
	@Secured(AuthoritiesConstants.USER)
	public ResponseEntity<List<DimHeat>> getHeatss(@RequestBody RcLogDTO details) {
		List<DimHeat> result = heatService.getHeats(details.getFromDate(), details.getToDate(),
				details.getPage(), details.getSize());
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
		

	@PostMapping("/heatdetails/reportdownload")
	@Timed
	@Secured(AuthoritiesConstants.USER)
	public void downloadHeatDetails(@RequestBody RcLogDTO details, HttpServletResponse response) throws IOException {
		List<FactHeatDetails> result = heatService.getHeatDetails(details.getFromDate(), details.getToDate(),
				details.getPage(), details.getSize());
		csvService.writeAll(response.getWriter(), result, FactHeatDetails.class);
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
