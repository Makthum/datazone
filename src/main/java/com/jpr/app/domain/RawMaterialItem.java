package com.jpr.app.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "rawmaterialitem")
public class RawMaterialItem {
	
	
	@Id
	private RawMaterialItemPk id;
	
	@MapsId("scrapId")
	@ManyToOne
	@JoinColumn(name = "dimScrapId", referencedColumnName = "id")
	@OnDelete(action =OnDeleteAction.CASCADE)
	private DimScrap dimScrap;
	
	@MapsId("heatId")
	@ManyToOne(cascade = { CascadeType.MERGE })
	@JoinColumn(name = "dimHeatId", referencedColumnName = "id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private DimHeat dimHeat;
	
	private Double quantity;

	public RawMaterialItemPk getId() {
		return id;
	}

	public void setId(RawMaterialItemPk id) {
		this.id = id;
	}

	public DimScrap getDimScrap() {
		return dimScrap;
	}

	public void setDimScrap(DimScrap dimScrap) {
		this.dimScrap = dimScrap;
	}

	public DimHeat getDimHeat() {
		return dimHeat;
	}

	public void setDimHeat(DimHeat dimHeat) {
		this.dimHeat = dimHeat;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	
	

}
