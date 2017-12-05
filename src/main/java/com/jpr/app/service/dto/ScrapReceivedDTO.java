package com.jpr.app.service.dto;

import java.util.Date;

public class ScrapReceivedDTO {
	
	private Date date;
	private int scrapType;
	private String scrapTypeName;
	private int quantity;
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
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	

}
