package com.jpr.app.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class HeatPk implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int heatId;

	public int getHeatId() {
		return heatId;
	}

	public void setHeatId(int heatId) {
		this.heatId = heatId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
