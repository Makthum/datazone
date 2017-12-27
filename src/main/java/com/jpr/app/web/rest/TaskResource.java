package com.jpr.app.web.rest;

import java.text.ParseException;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.jpr.app.domain.Task;
import com.jpr.app.security.AuthoritiesConstants;
import com.jpr.app.service.TaskService;
import com.jpr.app.service.dto.RcLogDTO;
import com.jpr.app.service.dto.TaskDTO;

@RestController
@RequestMapping("/api")
public class TaskResource {

	@Autowired
	TaskService tService;

	@PostMapping("/tasks")
	@Timed
	@Secured(AuthoritiesConstants.USER)
	public ResponseEntity<Task> createTasks(@RequestBody TaskDTO details) {
		Task result = tService.createTask(details);
		return new ResponseEntity<>(result, HttpStatus.OK);

	}

	@PostMapping("/tasks/report")
	@Timed
	@Secured(AuthoritiesConstants.USER)
	public ResponseEntity<List<Task>> getReport(@RequestBody RcLogDTO details) {
		List<Task> result = tService.getReport(details.getFromDate(), details.getToDate());
		return new ResponseEntity<>(result, HttpStatus.OK);

	}

	@GetMapping("/tasks")
	@Timed
	@Secured(AuthoritiesConstants.USER)
	public ResponseEntity<List<Task>> getEquipments() {
		List<Task> result = tService.getAllOpened();
		return new ResponseEntity<>(result, HttpStatus.OK);

	}

	@GetMapping("/tasks/{taskId}")
	@Timed
	@Secured(AuthoritiesConstants.USER)
	public ResponseEntity<Task> getTask(@PathVariable("taskId") Integer taskId) {
		if (taskId == null)
			return new ResponseEntity<>(null, HttpStatus.OK);
		Task result = tService.getTask(taskId);
		return new ResponseEntity<>(result, HttpStatus.OK);

	}

	@GetMapping("/tasks/update")
	@Timed
	@Secured(AuthoritiesConstants.USER)
	public ResponseEntity<List<Task>> updateTasks() throws ParseException {
		tService.createBatchTask();
		List<Task> result = tService.getAllOpened();
		return new ResponseEntity<>(result, HttpStatus.OK);

	}

}
