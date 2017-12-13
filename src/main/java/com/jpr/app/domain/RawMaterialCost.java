package com.jpr.app.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name="rawmaterialcost")
public class RawMaterialCost {

	@Id
	private RawMaterialCostPk id;

	@MapsId("dateId")
	@ManyToOne
	@JoinColumn(name = "dateId", referencedColumnName = "date_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private DimDate dimDate;

	@MapsId("scrapId")
	@ManyToOne
	@JoinColumn(name = "scrapId", referencedColumnName = "id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private DimScrap dimScrap;

	private Integer price;

	public RawMaterialCostPk getId() {
		return id;
	}

	public void setId(RawMaterialCostPk id) {
		this.id = id;
	}

	public DimDate getDimDate() {
		return dimDate;
	}

	public void setDimDate(DimDate dimDate) {
		this.dimDate = dimDate;
	}

	public DimScrap getDimScrap() {
		return dimScrap;
	}

	public void setDimScrap(DimScrap dimScrap) {
		this.dimScrap = dimScrap;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

}
