package com.jpr.app.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class RawMaterialItemPk implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer heatId;
	private Integer scrapId;
	public Integer getHeatId() {
		return heatId;
	}
	public void setHeatId(Integer heatId) {
		this.heatId = heatId;
	}
	public Integer getScrapId() {
		return scrapId;
	}
	public void setScrapId(Integer scrapId) {
		this.scrapId = scrapId;
	}
	

}
