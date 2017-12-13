package com.jpr.app.service.dto;

import java.util.HashMap;

public class DailyReportDTO {

	private Double production;
	private Double slag;
	private Integer totalRunningTime;
	private Integer totalBreakdown;
	private Integer averageRunningTime;
	private Long totalUnits;
	private Integer averageUpt;
	private Integer averageTappingTime;
	private Double powerFactor;
	private Double averageAlloyCost;
	private Double rawMaterialCost;
	private HashMap<String, Integer> composition;

	public Double getProduction() {
		return production;
	}

	public void setProduction(Double production) {
		this.production = production;
	}

	public Double getSlag() {
		return slag;
	}

	public void setSlag(Double slag) {
		this.slag = slag;
	}

	public Integer getTotalRunningTime() {
		return totalRunningTime;
	}

	public void setTotalRunningTime(Integer totalRunningTime) {
		this.totalRunningTime = totalRunningTime;
	}

	public Integer getTotalBreakdown() {
		return totalBreakdown;
	}

	public void setTotalBreakdown(Integer totalBreakdown) {
		this.totalBreakdown = totalBreakdown;
	}

	public Integer getAverageRunningTime() {
		return averageRunningTime;
	}

	public void setAverageRunningTime(Integer averageRunningTime) {
		this.averageRunningTime = averageRunningTime;
	}

	public Long getTotalUnits() {
		return totalUnits;
	}

	public void setTotalUnits(Long totalUnits) {
		this.totalUnits = totalUnits;
	}

	public Integer getAverageUpt() {
		return averageUpt;
	}

	public void setAverageUpt(Integer averageUpt) {
		this.averageUpt = averageUpt;
	}

	public Integer getAverageTappingTime() {
		return averageTappingTime;
	}

	public void setAverageTappingTime(Integer averageTappingTime) {
		this.averageTappingTime = averageTappingTime;
	}

	public Double getPowerFactor() {
		return powerFactor;
	}

	public void setPowerFactor(Double powerFactor) {
		this.powerFactor = powerFactor;
	}

	public Double getAverageAlloyCost() {
		return averageAlloyCost;
	}

	public void setAverageAlloyCost(Double averageAlloyCost) {
		this.averageAlloyCost = averageAlloyCost;
	}

	public Double getRawMaterialCost() {
		return rawMaterialCost;
	}

	public void setRawMaterialCost(Double rawMaterialCost) {
		this.rawMaterialCost = rawMaterialCost;
	}

	public HashMap<String, Integer> getComposition() {
		return composition;
	}

	public void setComposition(HashMap<String, Integer> composition) {
		this.composition = composition;
	}

}
