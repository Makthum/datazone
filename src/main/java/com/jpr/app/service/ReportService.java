package com.jpr.app.service;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.jpr.app.domain.DimScrap;
import com.jpr.app.domain.FactScrapIssued;
import com.jpr.app.domain.FactScrapRecv;
import com.jpr.app.domain.RawMaterialCost;
import com.jpr.app.repository.FactHeatDetailsRepository;
import com.jpr.app.repository.RawMaterialCostRepository;
import com.jpr.app.service.dto.CompositionCost;
import com.jpr.app.service.dto.DailyReportDTO;
import com.jpr.app.service.dto.MonthlyScrapReportDTO;

@Service
public class ReportService {

	@Autowired
	FactHeatDetailsRepository detailsRepo;

	@Autowired
	ScrapService scrapService;

	@Autowired
	RawMaterialCostRepository costRepo;

	@Autowired
	CSVService csvService;

	@Autowired
	JdbcTemplate template;

	public DailyReportDTO getDailyReport(Date fromDate, Date toDate) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String fDate = format.format(fromDate);
		String tDate = format.format(toDate);
		Object[] temp = detailsRepo.getDailyReport(fDate, tDate).get(0);
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

	public List<CompositionCost> getCostComposition(Date fromDate, Date toDate) {
		Map<DimScrap, Double> result = scrapService.getCompositionByScrap(fromDate, toDate);
		List<RawMaterialCost> costs = costRepo.findByDimDateDateBetween(fromDate, toDate);
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

	public List<MonthlyScrapReportDTO> getMonthlyReceivedReport(Date fromDate, Date toDate, int page, int size) {
		List<FactScrapRecv> received = scrapService.getScrapReceivedLogs(fromDate, toDate, page, size);
		List<MonthlyScrapReportDTO> result = new ArrayList<>();
		for (FactScrapRecv entry : received) {
			MonthlyScrapReportDTO temp = new MonthlyScrapReportDTO();
			temp.setId(entry.getDimScrap().getId());
			temp.setName(entry.getDimScrap().getName());
			if (result.contains(temp)) {
				temp = result.get(result.indexOf(temp));
				temp.setQuantity(temp.getQuantity() + entry.getQuantity());
			} else {
				temp.setQuantity(entry.getQuantity());
				result.add(temp);
			}

		}
		return result;
	}

	public List<MonthlyScrapReportDTO> getMonthlyIssuedReport(Date fromDate, Date toDate, int page, int size) {
		List<FactScrapIssued> received = scrapService.getScrapIssuedLogs(fromDate, toDate, page, size);
		List<MonthlyScrapReportDTO> result = new ArrayList<>();
		for (FactScrapIssued entry : received) {
			MonthlyScrapReportDTO temp = new MonthlyScrapReportDTO();
			temp.setId(entry.getDimScrap().getId());
			temp.setName(entry.getDimScrap().getName());
			if (result.contains(temp)) {
				temp = result.get(result.indexOf(temp));
				temp.setQuantity(temp.getQuantity() + entry.getQuantity());
			} else {
				temp.setQuantity(entry.getQuantity());
				result.add(temp);
			}

		}
		return result;
	}

	public JSONArray customReport(String query) throws JSONException {
		List<Map<String, Object>> set = template.queryForList(query);
		JSONArray array = new JSONArray();
		for (Map<String, Object> entry : set) {
			array.put(toJson(entry));
		}
		return array;
	}

	public List<Map<String, Object>> downloadCustomReport(String query) {
		List<Map<String, Object>> set = template.queryForList(query);
		return set;
	}

	private JSONObject toJson(Map<String, Object> map) throws JSONException {
		JSONObject jsonObject = new JSONObject();

		for (String key : map.keySet()) {

			Object obj = map.get(key);
			if (obj instanceof Map) {
				jsonObject.put(key, toJson((Map) obj));
			} else if (obj instanceof List) {
				jsonObject.put(key, toJson((List) obj));
			} else {
				jsonObject.put(key, map.get(key));
			}
		}

		return jsonObject;
	}

	private JSONArray toJson(List<Object> list) throws JSONException {
		JSONArray jsonArray = new JSONArray();

		for (Object obj : list) {
			if (obj instanceof Map) {
				jsonArray.put(toJson((Map) obj));
			} else if (obj instanceof List) {
				jsonArray.put(toJson((List) obj));
			} else {
				jsonArray.put(obj);
			}
		}

		return jsonArray;
	}
}
