package com.jpr.app.service.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.Id;

import com.jpr.app.domain.Equipment;

public class TaskDTO {

	private String description;

	private Date endDate;

	private Integer equipmentId;

	private Integer id;

	private List<Integer> personnelIds;

	private String remarks;

	private Integer scheduleId;

	private String spareUsed;

	private Date startDate;

	private String taskType;
	
	private String status;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(Integer equipmentId) {
		this.equipmentId = equipmentId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Integer> getPersonnelIds() {
		return personnelIds;
	}

	public void setPersonnelIds(List<Integer> personnelIds) {
		this.personnelIds = personnelIds;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(Integer scheduleId) {
		this.scheduleId = scheduleId;
	}

	public String getSpareUsed() {
		return spareUsed;
	}

	public void setSpareUsed(String spareUsed) {
		this.spareUsed = spareUsed;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
