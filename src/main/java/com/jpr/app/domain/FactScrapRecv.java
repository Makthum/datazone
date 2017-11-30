package com.jpr.app.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "factscraprecv")
public class FactScrapRecv {

	@ManyToOne(optional = false)
	@JoinColumn(name = "Id")
	private DimScrap scrap;

	public DimScrap getScrap() {
		return scrap;
	}

	public void setScrap(DimScrap scrap) {
		this.scrap = scrap;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public DimDate getDate_id() {
		return date_id;
	}

	public void setDate_id(DimDate date_id) {
		this.date_id = date_id;
	}

	private int quantity;
	@ManyToOne(optional = false)
	@JoinColumn(name = "date_id")
	private DimDate date_id;

}
