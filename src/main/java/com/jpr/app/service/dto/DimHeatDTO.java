package com.jpr.app.service.dto;

import java.util.Date;

public class DimHeatDTO {
	
	private int id;
	private String heatId;
	private Character furnaceId;
	private int sinteringHeatId;
	private Date dateOn;
	private Date dateOff;
	public String getHeatId() {
		return heatId;
	}
	public void setHeatId(String heatId) {
		this.heatId = heatId;
	}
	public Character getFurnaceId() {
		return furnaceId;
	}
	public void setFurnaceId(Character furnaceId) {
		this.furnaceId = furnaceId;
	}
	public int getSinteringHeatId() {
		return sinteringHeatId;
	}
	public void setSinteringHeatId(int sinteringHeatId) {
		this.sinteringHeatId = sinteringHeatId;
	}
	public Date getDateOn() {
		return dateOn;
	}
	public void setDateOn(Date dateOn) {
		this.dateOn = dateOn;
	}
	public Date getDateOff() {
		return dateOff;
	}
	public void setDateOff(Date dateOff) {
		this.dateOff = dateOff;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	

}
