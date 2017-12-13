package com.jpr.app.service.dto;

import java.util.Date;

public class IssueDTO {

	private int componentId;
	private Date date;
	private String description;
	private String workedBy;
	private String solution;
	private int timeTaken;

	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getComponentId() {
		return componentId;
	}

	public void setComponentId(int componentId) {
		this.componentId = componentId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getWorkedBy() {
		return workedBy;
	}

	public void setWorkedBy(String workedBy) {
		this.workedBy = workedBy;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public int getTimeTaken() {
		return timeTaken;
	}

	public void setTimeTaken(int timeTaken) {
		this.timeTaken = timeTaken;
	}


}
