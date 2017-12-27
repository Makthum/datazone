package com.jpr.app.service;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TaskUpdater {
	
	@Autowired
	TaskService service;
	
	@Scheduled(cron="0 * * * * ?")
	public void updateTasks() throws ParseException {
		service.createBatchTask();
	}

}
