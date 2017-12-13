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
@Table(name = "factheatmixture")
public class FactHeatMixture {

	private Double carbon;
	private Double manganese;
	private Double silicon;
	private Double phosphorous;
	private Double sulphur;
	private Integer silicoManganese;
	private Integer ferroManganese;
	@Id
	private int dimHeatId;

	@MapsId
	@ManyToOne(cascade = { CascadeType.MERGE })
	@JoinColumn(name = "dimHeatId", referencedColumnName = "id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private DimHeat dimHeat;

	public Double getCarbon() {
		return carbon;
	}

	public void setCarbon(Double carbon) {
		this.carbon = carbon;
	}

	public Double getManganese() {
		return manganese;
	}

	public void setManganese(Double manganese) {
		this.manganese = manganese;
	}

	public Double getSilicon() {
		return silicon;
	}

	public void setSilicon(Double silicon) {
		this.silicon = silicon;
	}

	public Double getPhosphorous() {
		return phosphorous;
	}

	public void setPhosphorous(Double phosphorous) {
		this.phosphorous = phosphorous;
	}

	public Double getSulphur() {
		return sulphur;
	}

	public void setSulphur(Double sulphur) {
		this.sulphur = sulphur;
	}

	public int getDimHeatId() {
		return dimHeatId;
	}

	public void setDimHeatId(int dimHeatId) {
		this.dimHeatId = dimHeatId;
	}

	public DimHeat getDimHeat() {
		return dimHeat;
	}

	public void setDimHeat(DimHeat dimHeat) {
		this.dimHeat = dimHeat;
	}

	public Integer getSilicoManganese() {
		return silicoManganese;
	}

	public void setSilicoManganese(Integer silicoManganese) {
		this.silicoManganese = silicoManganese;
	}

	public Integer getFerroManganese() {
		return ferroManganese;
	}

	public void setFerroManganese(Integer ferroManganese) {
		this.ferroManganese = ferroManganese;
	}
	
	

}
