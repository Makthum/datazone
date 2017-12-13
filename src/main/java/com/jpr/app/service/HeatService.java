package com.jpr.app.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.jpr.app.domain.DimHeat;
import com.jpr.app.domain.DimIssue;
import com.jpr.app.domain.FactHeatDetails;
import com.jpr.app.domain.FactHeatMixture;
import com.jpr.app.domain.FactHeatRawMaterialMixture;
import com.jpr.app.domain.RawMaterialItem;
import com.jpr.app.domain.RawMaterialItemPk;
import com.jpr.app.repository.DimHeatRepository;
import com.jpr.app.repository.DimScrapRepository;
import com.jpr.app.repository.FactHeatDetailsRepository;
import com.jpr.app.repository.FactHeatMixtureRepository;
import com.jpr.app.repository.FactHeatRawMaterialMixtureRepository;
import com.jpr.app.service.dto.HeatDetailsDTO;
import com.jpr.app.service.dto.HeatRawMaterialMixtureDTO;

@Service
public class HeatService {

	@Autowired
	DimHeatRepository dimHeatRepo;

	@Autowired
	IssueService issueService;

	@Autowired
	DimScrapRepository dimScrapRepo;

	@Autowired
	FactHeatDetailsRepository factHeatDetailsRepo;

	@Autowired
	FactHeatMixtureRepository heatMixtureRepo;

	@Autowired
	FactHeatRawMaterialMixtureRepository rawMaterialMixtureRepo;

	public DimHeat createDimHeat(DimHeat heat) {
		return dimHeatRepo.save(heat);
	}

	@Transactional
	public FactHeatDetails createHeatDetails(HeatDetailsDTO details) {
		FactHeatDetails result = factHeatDetailsRepo.findOne(details.getHeatId());
		if (result == null) {
			result = new FactHeatDetails();
			DimHeat heat = getHeat(details.getHeatId());
			result.setDimHeat(heat);

		}
		if (details.getIssueId() > 0) {
			DimIssue issue = issueService.getIssue(details.getIssueId());
			if (issue != null) {
				result.setIssue(issue);
				result.setDelay(details.getDelay());
			}
		} else {
			result.setIssue(null);
			result.setDelay(0);
		}
		result.setFurnaceOff(details.getFurnaceOff());
		result.setFurnaceOn(details.getFurnaceOn());
		result.setHeatType(details.getHeatType());
		result.setProduction(details.getProduction());
		result.setSlag(details.getSlag());
		result.setTappingTemp(details.getTappingTemp());
		result.setDelay(details.getDelay());
		result.setMaximumOperatingPower(details.getMaximumOperatingPower());
		result.setPowerFactor(details.getPowerFactor());
		result.setUnitConsumed(details.getUnitConsumed());
		result.setUnitPerTon(details.getUnitPerTon());
		result.setTappingTime(details.getTappingTime());
		result.setTimeTaken((int) TimeUnit.MILLISECONDS
				.toMinutes(result.getFurnaceOff().getTime() - result.getFurnaceOn().getTime()));

		return factHeatDetailsRepo.save(result);
	}

	public DimHeat getHeat(Integer id) {
		return dimHeatRepo.findOne(id);
	}

	public List<DimHeat> getHeats() {
		return dimHeatRepo.findAll();
	}
	
	public List<DimHeat> getRecentHeats() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -3);
		Date date = cal.getTime();
		return dimHeatRepo.findByDimDateDateAfter(date);
	}

	public List<FactHeatDetails> getHeatDetails(Date fromDate, Date toDate, int page, int size) {
		return factHeatDetailsRepo.findByDimHeatDimDateDateBetween(fromDate, toDate, new PageRequest(page, size));
	}

	public FactHeatMixture createHeatMixture(FactHeatMixture entity) {
		DimHeat heat = dimHeatRepo.findOne(entity.getDimHeatId());
		entity.setDimHeat(heat);
		return heatMixtureRepo.save(entity);
	}

	public FactHeatRawMaterialMixture createRawMaerialMix(HeatRawMaterialMixtureDTO dto) {
		FactHeatRawMaterialMixture result = rawMaterialMixtureRepo.findOne(dto.getHeatId());
		DimHeat heat = dimHeatRepo.findOne(dto.getHeatId());
		if (result == null) {
			result = new FactHeatRawMaterialMixture();
		}
		List<RawMaterialItem> scrapMix = new ArrayList<>();
		for (HeatRawMaterialMixtureDTO.ScrapQuantity scrap : dto.getScrapMix()) {
			RawMaterialItemPk pk = new RawMaterialItemPk();
			pk.setHeatId(dto.getHeatId());
			pk.setScrapId(scrap.getScrapId());
			RawMaterialItem item = new RawMaterialItem();
			item.setId(pk);
			item.setQuantity(scrap.getQuantity());
			item.setDimHeat(heat);
			item.setDimScrap(dimScrapRepo.getOne(scrap.getScrapId()));
			scrapMix.add(item);
		}
		result.setFerroManganese(dto.getFerroManganese());
		result.setScraps(scrapMix);
		result.setSilicoManganese(dto.getSilicoManganese());
		result.setDimHeat(heat);
		result.setDimHeatId(dto.getHeatId());
		return rawMaterialMixtureRepo.save(result);
	}

}
