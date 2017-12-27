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
import com.jpr.app.domain.MaintSchedule;
import com.jpr.app.security.AuthoritiesConstants;
import com.jpr.app.service.ScheduleService;
import com.jpr.app.service.dto.ScheduleDTO;

@RestController
@RequestMapping("/api")
public class ScheduleResource {

	@Autowired
	ScheduleService sService;

	@PostMapping("/schedules")
	@Timed
	@Secured(AuthoritiesConstants.USER)
	public ResponseEntity<MaintSchedule> createSchedule(@RequestBody ScheduleDTO details) {
		MaintSchedule result = sService.createSchedule(details);
		return new ResponseEntity<>(result, HttpStatus.OK);

	}

	@GetMapping("/schedules")
	@Timed
	@Secured(AuthoritiesConstants.USER)
	public ResponseEntity<List<MaintSchedule>> getEquipments() {
		List<MaintSchedule> result = sService.getAll();
		return new ResponseEntity<>(result, HttpStatus.OK);

	}

}
