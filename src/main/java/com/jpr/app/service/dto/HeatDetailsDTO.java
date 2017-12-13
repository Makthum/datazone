package com.jpr.app.service.dto;

import java.util.Date;

public class HeatDetailsDTO {

	private Date furnaceOn;
	private Date furnaceOff;
	private int timeTaken;
	private double slag;
	private String heatType;
	private int tappingTemp;
	private int unitConsumed;
	private int maximumOperatingPower;
	private int unitPerTon;
	private int tappingTime;
	private double powerFactor;

	public int getUnitConsumed() {
		return unitConsumed;
	}

	public void setUnitConsumed(int unitConsumed) {
		this.unitConsumed = unitConsumed;
	}

	public int getMaximumOperatingPower() {
		return maximumOperatingPower;
	}

	public void setMaximumOperatingPower(int maximumOperatingPower) {
		this.maximumOperatingPower = maximumOperatingPower;
	}

	public int getUnitPerTon() {
		return unitPerTon;
	}

	public void setUnitPerTon(int unitPerTon) {
		this.unitPerTon = unitPerTon;
	}

	public int getTappingTime() {
		return tappingTime;
	}

	public void setTappingTime(int tappingTime) {
		this.tappingTime = tappingTime;
	}

	public double getPowerFactor() {
		return powerFactor;
	}

	public void setPowerFactor(double powerFactor) {
		this.powerFactor = powerFactor;
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

	public int getTimeTaken() {
		return timeTaken;
	}

	public void setTimeTaken(int timeTaken) {
		this.timeTaken = timeTaken;
	}

	public double getSlag() {
		return slag;
	}

	public void setSlag(double slag) {
		this.slag = slag;
	}

	public double getProduction() {
		return production;
	}

	public void setProduction(double production) {
		this.production = production;
	}

	public String getHeatType() {
		return heatType;
	}

	public void setHeatType(String heatType) {
		this.heatType = heatType;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public int getHeatId() {
		return heatId;
	}

	public void setHeatId(int heatId) {
		this.heatId = heatId;
	}

	public int getIssueId() {
		return issueId;
	}

	public void setIssueId(int issueId) {
		this.issueId = issueId;
	}

	private int delay;
	private double production;
	private int heatId;
	private int issueId;

	public int getTappingTemp() {
		return tappingTemp;
	}

	public void setTappingTemp(int tappingTemp) {
		this.tappingTemp = tappingTemp;
	}

}
