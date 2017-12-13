package com.jpr.app.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "factheatdetails")
public class FactHeatDetails {

	@Id
	private int dimHeatId;

	@MapsId
	@ManyToOne(cascade = { CascadeType.MERGE })
	@JoinColumn(name = "dimHeatId", referencedColumnName = "id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private DimHeat dimHeat;
	private Integer tappingTemp;
	private Date furnaceOn;
	private Date furnaceOff;
	private Integer timeTaken;
	private Double slag;
	private String heatType;
	private Integer delay;
	private Double production;
	@ManyToOne
	@JoinColumn(name = "dimIssueId", referencedColumnName = "id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private DimIssue issue;

	private Integer unitConsumed;
	private Integer maximumOperatingPower;
	private Integer unitPerTon;
	private Integer tappingTime;
	private Double powerFactor;

	public int getDimHeatId() {
		return dimHeatId;
	}

	public void setDimHeatId(int dimHeatId) {
		this.dimHeatId = dimHeatId;
	}

	public DimHeat getDimHeat() {
		return dimHeat;
	}

	public void setDimHeat(DimHeat dimHeat) {
		this.dimHeat = dimHeat;
	}

	public Integer getTappingTemp() {
		return tappingTemp;
	}

	public void setTappingTemp(Integer tappingTemp) {
		this.tappingTemp = tappingTemp;
	}

	public Date getFurnaceOn() {
		return furnaceOn;
	}

	public void setFurnaceOn(Date furnaceOn) {
		this.furnaceOn = furnaceOn;
	}

	public Date getFurnaceOff() {
		return furnaceOff;
	}

	public void setFurnaceOff(Date furnaceOff) {
		this.furnaceOff = furnaceOff;
	}

	public Integer getTimeTaken() {
		return timeTaken;
	}

	public void setTimeTaken(Integer timeTaken) {
		this.timeTaken = timeTaken;
	}

	public Double getSlag() {
		return slag;
	}

	public void setSlag(Double slag) {
		this.slag = slag;
	}

	public String getHeatType() {
		return heatType;
	}

	public void setHeatType(String heatType) {
		this.heatType = heatType;
	}

	public Integer getDelay() {
		return delay;
	}

	public void setDelay(Integer delay) {
		this.delay = delay;
	}

	public Double getProduction() {
		return production;
	}

	public void setProduction(Double production) {
		this.production = production;
	}

	public DimIssue getIssue() {
		return issue;
	}

	public void setIssue(DimIssue issue) {
		this.issue = issue;
	}

	public Integer getUnitConsumed() {
		return unitConsumed;
	}

	public void setUnitConsumed(Integer unitConsumed) {
		this.unitConsumed = unitConsumed;
	}

	public Integer getMaximumOperatingPower() {
		return maximumOperatingPower;
	}

	public void setMaximumOperatingPower(Integer maximumOperatingPower) {
		this.maximumOperatingPower = maximumOperatingPower;
	}

	public Integer getUnitPerTon() {
		return unitPerTon;
	}

	public void setUnitPerTon(Integer unitPerTon) {
		this.unitPerTon = unitPerTon;
	}

	public Integer getTappingTime() {
		return tappingTime;
	}

	public void setTappingTime(Integer tappingTime) {
		this.tappingTime = tappingTime;
	}

	public Double getPowerFactor() {
		return powerFactor;
	}

	public void setPowerFactor(Double powerFactor) {
		this.powerFactor = powerFactor;
	}

}
