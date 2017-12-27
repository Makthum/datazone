package com.jpr.app.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "task")
public class Task {

	private String description;

	private Date endDate;

	@ManyToOne
	@JoinColumn(name = "equipment_id", referencedColumnName = "id")
	private Equipment equipment;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "task_personnel", joinColumns = {
			@JoinColumn(name = "task_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "personnel_id", referencedColumnName = "id", unique = true) })
	private List<Personnel> personnels;

	private String remarks;

	@ManyToOne
	@JoinColumn(name = "schedule_id",insertable=false,updatable=false,referencedColumnName="id")
	private MaintSchedule scheulde;

	private String spareUsed;

	private Date startDate;

	private String taskType;
	
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public Date getEndDate() {
		return endDate;
	}

	public Equipment getEquipment() {
		return equipment;
	}

	public Integer getId() {
		return id;
	}

	public List<Personnel> getPersonnels() {
		return personnels;
	}

	public String getRemarks() {
		return remarks;
	}

	public MaintSchedule getScheulde() {
		return scheulde;
	}

	public String getSpareUsed() {
		return spareUsed;
	}

	public Date getStartDate() {
		return startDate;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setPersonnels(List<Personnel> personnels) {
		this.personnels = personnels;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setScheulde(MaintSchedule scheulde) {
		this.scheulde = scheulde;
	}

	public void setSpareUsed(String spareUsed) {
		this.spareUsed = spareUsed;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

}
