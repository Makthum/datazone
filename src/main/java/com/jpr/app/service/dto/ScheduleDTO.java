package com.jpr.app.service.dto;

import java.util.Date;

public class ScheduleDTO {

	private Date endDate;
	private Integer equipmentId;
	private Integer frequency;
	private Integer id;
	private Date lastService;
	private Date nextService;
	private Integer noOfPersonnel;
	private String spareRequired;
	private Date startDate;
	private String taskCreated;

	private String team;

	private String toolsRequired;
	private String workType;
	public Date getEndDate() {
		return endDate;
	}
	public Integer getEquipmentId() {
		return equipmentId;
	}

	public Integer getFrequency() {
		return frequency;
	}

	public Integer getId() {
		return id;
	}

	public Date getLastService() {
		return lastService;
	}

	public Date getNextService() {
		return nextService;
	}

	public Integer getNoOfPersonnel() {
		return noOfPersonnel;
	}

	public String getSpareRequired() {
		return spareRequired;
	}

	public Date getStartDate() {
		return startDate;
	}

	public String getTaskCreated() {
		return taskCreated;
	}

	public String getTeam() {
		return team;
	}

	public String getToolsRequired() {
		return toolsRequired;
	}

	public String getWorkType() {
		return workType;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setEquipmentId(Integer equipmentId) {
		this.equipmentId = equipmentId;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setLastService(Date lastService) {
		this.lastService = lastService;
	}

	public void setNextService(Date nextService) {
		this.nextService = nextService;
	}

	public void setNoOfPersonnel(Integer noOfPersonnel) {
		this.noOfPersonnel = noOfPersonnel;
	}

	public void setSpareRequired(String spareRequired) {
		this.spareRequired = spareRequired;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setTaskCreated(String taskCreated) {
		this.taskCreated = taskCreated;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public void setToolsRequired(String toolsRequired) {
		this.toolsRequired = toolsRequired;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
	}

}
