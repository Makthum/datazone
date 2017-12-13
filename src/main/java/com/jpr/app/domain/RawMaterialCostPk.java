package com.jpr.app.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class RawMaterialCostPk implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer dateId;
	private Integer scrapId;

	public Integer getDateId() {
		return dateId;
	}

	public void setDateId(Integer dateId) {
		this.dateId = dateId;
	}

	public Integer getScrapId() {
		return scrapId;
	}

	public void setScrapId(Integer scrapId) {
		this.scrapId = scrapId;
	}

}
