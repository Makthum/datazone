package com.jpr.app.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "factscraprecv")
public class FactScrapRecv {

	

	@EmbeddedId
	private ScrapRecvPk id;

	@MapsId("scrapId")
	@ManyToOne
	@JoinColumn(name = "dimScrapId", referencedColumnName = "id")
	@OnDelete(action =OnDeleteAction.CASCADE)
	private DimScrap dimScrap;

	@MapsId("dateId")
	@ManyToOne
	@JoinColumn(name = "dimDateId", referencedColumnName = "date_id")
	@OnDelete(action =OnDeleteAction.CASCADE)
	private DimDate dimDate;
	
	private Double quantity;

	public ScrapRecvPk getId() {
		return id;
	}

	public void setId(ScrapRecvPk id) {
		this.id = id;
	}

	public DimScrap getDimScrap() {
		return dimScrap;
	}

	public void setDimScrap(DimScrap dimScrap) {
		this.dimScrap = dimScrap;
	}

	public DimDate getDimDate() {
		return dimDate;
	}

	public void setDimDate(DimDate dimDate) {
		this.dimDate = dimDate;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	

	
	
}
