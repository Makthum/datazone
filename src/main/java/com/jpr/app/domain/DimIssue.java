package com.jpr.app.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "dimissue")
public class DimIssue {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name = "dimComponentId", referencedColumnName = "id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private DimComponent component;

	@ManyToOne
	@JoinColumn(name = "dimDateId", referencedColumnName = "date_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private DimDate dimDate;

	public DimComponent getComponent() {
		return component;
	}

	public void setComponent(DimComponent component) {
		this.component = component;
	}

	private String issueDescription;
	private String workedBy;
	private String issueResolution;
	private int timeTaken;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIssueDescription() {
		return issueDescription;
	}

	public void setIssueDescription(String issueDescription) {
		this.issueDescription = issueDescription;
	}

	public String getWorkedBy() {
		return workedBy;
	}

	public void setWorkedBy(String workedBy) {
		this.workedBy = workedBy;
	}

	public String getIssueResolution() {
		return issueResolution;
	}

	public void setIssueResolution(String issueResolution) {
		this.issueResolution = issueResolution;
	}

	public int getTimeTaken() {
		return timeTaken;
	}

	public void setTimeTaken(int timeTaken) {
		this.timeTaken = timeTaken;
	}

	public DimDate getDimDate() {
		return dimDate;
	}

	public void setDimDate(DimDate dimDate) {
		this.dimDate = dimDate;
	}

}
