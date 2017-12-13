package com.jpr.app.service.dto;

import java.util.List;

public class HeatRawMaterialMixtureDTO {

	private Integer heatId;
	private Double silicoManganese;
	private Double ferroManganese;
	private List<ScrapQuantity> scrapMix;
	
	
	public Integer getHeatId() {
		return heatId;
	}


	public void setHeatId(Integer heatId) {
		this.heatId = heatId;
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


	public List<ScrapQuantity> getScrapMix() {
		return scrapMix;
	}


	public void setScrapMix(List<ScrapQuantity> scrapMix) {
		this.scrapMix = scrapMix;
	}


	public static class ScrapQuantity {
		
		private Integer scrapId;
		private Double quantity;
		public Integer getScrapId() {
			return scrapId;
		}
		public void setScrapId(Integer scrapId) {
			this.scrapId = scrapId;
		}
		public Double getQuantity() {
			return quantity;
		}
		public void setQuantity(Double quantity) {
			this.quantity = quantity;
		}
		
	}
}
