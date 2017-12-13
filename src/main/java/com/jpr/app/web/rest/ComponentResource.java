package com.jpr.app.web.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.jpr.app.domain.DimComponent;
import com.jpr.app.security.AuthoritiesConstants;
import com.jpr.app.service.ComponentService;

@RestController
@RequestMapping("/api")
public class ComponentResource {

	@Autowired
	ComponentService compoService;

	@PostMapping("/components")
	@Timed
	@Secured(AuthoritiesConstants.USER)
	public ResponseEntity<DimComponent> createComponent(@RequestBody DimComponent details) {
		DimComponent result = compoService.createComponent(details);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/components")
	@Timed
	@Secured(AuthoritiesConstants.USER)
	public ResponseEntity<List<DimComponent>> getComponent() {
		List<DimComponent> result = compoService.getComponents();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PutMapping("/components")
	@Timed
	@Secured(AuthoritiesConstants.USER)
	public ResponseEntity<DimComponent> updateComponent(@RequestBody DimComponent details) {
		DimComponent result = compoService.createComponent(details);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

}
