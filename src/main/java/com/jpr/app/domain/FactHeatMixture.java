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
	private Double silicoManganese;
	private Double ferroManganese;
	private Double aluminium;
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

	public Double getSilicoManganese() {
		return silicoManganese;
	}

	public void setSilicoManganese(Double silicoManganese) {
		this.silicoManganese = silicoManganese;
	}

	public Double getFerroManganese() {
		return ferroManganese;
	}

	public void setFerroManganese(Double ferroManganese) {
		this.ferroManganese = ferroManganese;
	}

	public Double getAluminium() {
		return aluminium;
	}

	public void setAluminium(Double aluminium) {
		this.aluminium = aluminium;
	}

	
	
	

}
