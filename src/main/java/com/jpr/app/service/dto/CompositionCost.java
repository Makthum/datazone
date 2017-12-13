package com.jpr.app.service.dto;

public class CompositionCost {

	private Integer scrapId;
	private Double Quantity;
	private Double price;
	private String scrapName;

	public Integer getScrapId() {
		return scrapId;
	}

	public void setScrapId(Integer scrapId) {
		this.scrapId = scrapId;
	}

	public Double getQuantity() {
		return Quantity;
	}

	public void setQuantity(Double quantity) {
		Quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getScrapName() {
		return scrapName;
	}

	public void setScrapName(String scrapName) {
		this.scrapName = scrapName;
	}
	
	

}
