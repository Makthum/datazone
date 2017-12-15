package com.jpr.app.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "dimheat")
public class DimHeat {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String heatId;
	private Character furnaceId;
	private int sinteringHeatId;

	@ManyToOne
	@JoinColumn(name = "dimDateOnId", referencedColumnName = "date_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private DimDate dimDateOn;

	@ManyToOne
	@JoinColumn(name = "dimDateOffId", referencedColumnName = "date_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private DimDate dimDateOff;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public DimDate getDimDateOn() {
		return dimDateOn;
	}

	public void setDimDateOn(DimDate dimDateOn) {
		this.dimDateOn = dimDateOn;
	}

	public DimDate getDimDateOff() {
		return dimDateOff;
	}

	public void setDimDateOff(DimDate dimDateOff) {
		this.dimDateOff = dimDateOff;
	}

}
