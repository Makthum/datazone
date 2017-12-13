package com.jpr.app.service.dto;

import java.util.Date;

public class CostDTO {

	private Integer scrapId;
	private Date date;
	private Integer price;

	public Integer getScrapId() {
		return scrapId;
	}

	public void setScrapId(Integer scrapId) {
		this.scrapId = scrapId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

}
