package com.jpr.app.service.dto;

import java.util.Date;

public class ScrapReceivedDTO {
	
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	private Date date;
	private int scrapType;
	private String scrapTypeName;
	private Double quantity;
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getScrapType() {
		return scrapType;
	}
	public void setScrapType(int scrapType) {
		this.scrapType = scrapType;
	}
	public String getScrapTypeName() {
		return scrapTypeName;
	}
	public void setScrapTypeName(String scrapTypeName) {
		this.scrapTypeName = scrapTypeName;
	}
	
	

}
