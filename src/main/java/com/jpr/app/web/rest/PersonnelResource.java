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
import com.jpr.app.domain.Personnel;
import com.jpr.app.security.AuthoritiesConstants;
import com.jpr.app.service.PersonnelService;

@RestController
@RequestMapping("/api")
public class PersonnelResource {

	@Autowired
	PersonnelService pService;

	@PostMapping("/personnels")
	@Timed
	@Secured(AuthoritiesConstants.USER)
	public ResponseEntity<Personnel> createPersonnel(@RequestBody Personnel details) {
		Personnel result = pService.createPersonnel(details);
		return new ResponseEntity<>(result, HttpStatus.OK);

	}
	
	@GetMapping("/personnels")
	@Timed
	@Secured(AuthoritiesConstants.USER)
	public ResponseEntity<List<Personnel>> getPersonnels() {
		List<Personnel> result = pService.getAll();
		return new ResponseEntity<>(result, HttpStatus.OK);

	}
}
