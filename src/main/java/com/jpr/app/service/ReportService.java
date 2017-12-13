package com.jpr.app.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jpr.app.domain.DimScrap;
import com.jpr.app.domain.RawMaterialCost;
import com.jpr.app.repository.FactHeatDetailsRepository;
import com.jpr.app.repository.RawMaterialCostRepository;
import com.jpr.app.service.dto.CompositionCost;
import com.jpr.app.service.dto.DailyReportDTO;

@Service
public class ReportService {

	@Autowired
	FactHeatDetailsRepository detailsRepo;

	@Autowired
	ScrapService scrapService;

	@Autowired
	RawMaterialCostRepository costRepo;

	public DailyReportDTO getDailyReport(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String dateValue = format.format(date);
		Object[] temp = detailsRepo.getDailyReport(dateValue).get(0);
		if (temp[0] == null) {
			return null;
		}
		DailyReportDTO result = new DailyReportDTO();
		result.setAverageRunningTime(((BigDecimal) temp[6]).intValue());
		result.setAverageTappingTime(((BigDecimal) temp[7]).intValue());
		result.setAverageUpt(((BigDecimal) temp[8]).intValue());
		result.setPowerFactor((Double) temp[4]);
		result.setProduction((Double) temp[0]);
		result.setSlag((Double) temp[1]);
		result.setTotalBreakdown(((BigDecimal) temp[2]).intValue());
		result.setTotalRunningTime(((BigDecimal) temp[5]).intValue());
		result.setTotalUnits(((BigDecimal) temp[3]).longValue());
		return result;
	}

	public List<CompositionCost> getCostComposition(Date date) {
		Map<DimScrap, Double> result = scrapService.getCompositionByScrap(date);
		List<RawMaterialCost> costs = costRepo.findByDimDateDate(date);
		List<CompositionCost> price = new ArrayList<>();
		for (Map.Entry<DimScrap, Double> entry : result.entrySet()) {
			CompositionCost temp = new CompositionCost();
			temp.setScrapId(entry.getKey().getId());
			temp.setQuantity(entry.getValue());
			temp.setScrapName(entry.getKey().getName());
			temp.setPrice(getCost(costs, entry.getKey().getId()));
			price.add(temp);
		}
		return price;

	}

	private Double getCost(List<RawMaterialCost> cost, Integer scrapId) {
		for (RawMaterialCost entry : cost) {
			if (entry.getDimScrap().getId() == scrapId) {
				return new Double(entry.getPrice());
			}
		}
		return new Double(0);

	}
}
