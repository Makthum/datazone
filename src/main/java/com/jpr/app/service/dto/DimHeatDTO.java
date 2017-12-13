package com.jpr.app.service.dto;

import java.util.Date;

public class DimHeatDTO {
	
	private String heatId;
	private Character furnaceId;
	private int sinteringHeatId;
	private Date date;
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
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	

}
