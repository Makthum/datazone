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
import com.jpr.app.domain.Equipment;
import com.jpr.app.domain.Personnel;
import com.jpr.app.security.AuthoritiesConstants;
import com.jpr.app.service.EquipmentService;
import com.jpr.app.service.dto.EquipmentDTO;

@RestController
@RequestMapping("/api")
public class EquipmentResource {

	@Autowired
	EquipmentService eService;

	@PostMapping("/equipments")
	@Timed
	@Secured(AuthoritiesConstants.USER)
	public ResponseEntity<Equipment> createSchedule(@RequestBody EquipmentDTO details) {
		Equipment result = eService.createEquipment(details);
		return new ResponseEntity<>(result, HttpStatus.OK);

	}
	
	@GetMapping("/equipments")
	@Timed
	@Secured(AuthoritiesConstants.USER)
	public ResponseEntity<List<Equipment>> getEquipments() {
		List<Equipment> result = eService.getAll();
		return new ResponseEntity<>(result, HttpStatus.OK);

	}

}
