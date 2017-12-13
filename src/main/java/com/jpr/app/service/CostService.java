package com.jpr.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jpr.app.domain.DimDate;
import com.jpr.app.domain.DimScrap;
import com.jpr.app.domain.RawMaterialCost;
import com.jpr.app.domain.RawMaterialCostPk;
import com.jpr.app.repository.DimDateRepository;
import com.jpr.app.repository.DimScrapRepository;
import com.jpr.app.repository.RawMaterialCostRepository;
import com.jpr.app.service.dto.CostDTO;

@Service
public class CostService {

	@Autowired
	RawMaterialCostRepository costRepo;

	@Autowired
	private DimScrapRepository scrapRepo;

	@Autowired
	private DimDateRepository dateRepo;

	public RawMaterialCost saveCost(CostDTO dto) {
		RawMaterialCostPk pk = new RawMaterialCostPk();
		DimDate date = dateRepo.findByDate(dto.getDate());
		DimScrap scrap = scrapRepo.getOne(dto.getScrapId());
		pk.setDateId(date.getDate_id());
		pk.setScrapId(dto.getScrapId());
		RawMaterialCost result = new RawMaterialCost();
		result.setId(pk);
		result.setDimDate(date);
		result.setDimScrap(scrap);
		result.setPrice(dto.getPrice());
		return costRepo.save(result);
	}

}
