package com.jpr.app.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Entity
@Table(name = "factheatrawmaterialmixture")
public class FactHeatRawMaterialMixture {

	@Id
	private int dimHeatId;

	@MapsId
	@ManyToOne(cascade = { CascadeType.MERGE })
	@JoinColumn(name = "dimHeatId", referencedColumnName = "id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private DimHeat dimHeat;

	private Double silicoManganese;
	private Double ferroManganese;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<RawMaterialItem> scraps;
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
	public List<RawMaterialItem> getScraps() {
		return scraps;
	}
	public void setScraps(List<RawMaterialItem> scraps) {
		this.scraps = scraps;
	}
	
	

}
