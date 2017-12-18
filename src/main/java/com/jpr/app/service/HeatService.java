package com.jpr.app.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
		return dimHeatRepo.findByDimDateOnDateAfter(date);
	}

	public List<FactHeatDetails> getHeatDetails(Date fromDate, Date toDate, int page, int size) {
		return factHeatDetailsRepo.findByDimHeatDimDateOnDateBetween(fromDate, toDate, new PageRequest(page, size));
	}

	public HeatDetailsDTO getHeatDetails(Integer id) {
		FactHeatDetails temp = factHeatDetailsRepo.findOne(id);
		if (temp == null)
			return null;
		HeatDetailsDTO result = new HeatDetailsDTO();
		result.setDelay(temp.getDelay());
		result.setFurnaceOff(temp.getFurnaceOff());
		result.setFurnaceOn(temp.getFurnaceOn());
		result.setHeatId(temp.getDimHeat().getId());
		result.setHeatType(temp.getHeatType());
		if (temp.getIssue() != null)
			result.setIssueId(temp.getIssue().getId());
		result.setMaximumOperatingPower(result.getMaximumOperatingPower());
		result.setPowerFactor(temp.getPowerFactor());
		result.setProduction(temp.getProduction());
		result.setSlag(temp.getSlag());
		result.setTappingTemp(temp.getTappingTemp());
		result.setTappingTime(temp.getTappingTime());
		result.setTimeTaken(temp.getTimeTaken());
		result.setUnitConsumed(temp.getUnitConsumed());
		result.setUnitPerTon(temp.getUnitPerTon());
		return result;
	}

	public FactHeatMixture getHeatMix(Integer id) {
		return heatMixtureRepo.findOne(id);
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

	public List<Map<Date, Double>> getProduction(Date fromDate, Date toDate, String[] fields) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		List<Object[]> resultList = factHeatDetailsRepo.getProduction(format.format(fromDate), format.format(toDate));
		List<Map<Date, Double>> results = new ArrayList<>();
		for (String field : fields) {
			int fieldId = getField(field);
			Map<Date, Double> temp = new HashMap<>();
			for (Object[] borderTypes : resultList) {
				Date date = (Date) borderTypes[0];
				temp.put(date, getFieldValue(field, borderTypes[fieldId]));
			}
			results.add(temp);
		}
		return results;
	}

	private int getField(String fieldName) {
		if (fieldName.equalsIgnoreCase("production"))
			return 1;
		else if (fieldName.equalsIgnoreCase("RunningTime"))
			return 2;
		else if (fieldName.equalsIgnoreCase("upt"))
			return 3;
		else if (fieldName.equalsIgnoreCase("breakdown"))
			return 4;
		else if (fieldName.equalsIgnoreCase("powerFactor"))
			return 5;
		else if (fieldName.equalsIgnoreCase("maximumDemand"))
			return 6;
		else if (fieldName.equalsIgnoreCase("tappingTemperature"))
			return 7;
		else
			return -1;

	}

	private Double getFieldValue(String fieldName, Object obj) {
		if (fieldName.equalsIgnoreCase("production"))
			return ((Double) obj);
		else if (fieldName.equalsIgnoreCase("RunningTime"))
			return ((BigDecimal) obj).doubleValue();
		else if (fieldName.equalsIgnoreCase("upt"))
			return ((BigDecimal) obj).doubleValue();
		else if (fieldName.equalsIgnoreCase("breakdown"))
			return ((BigDecimal) obj).doubleValue();
		else if (fieldName.equalsIgnoreCase("powerFactor"))
			return ((Double) obj);
		else if (fieldName.equalsIgnoreCase("maximumDemand"))
			return ((BigDecimal) obj).doubleValue();
		else if (fieldName.equalsIgnoreCase("tappingTemperature"))
			return ((Double) obj);
		else
			return new Double(-1);

	}

	private JSONArray getCategories(List<Map<Date, Double>> details) throws JSONException {
		JSONArray array = new JSONArray();
		JSONObject obj = new JSONObject();
		JSONArray cat = new JSONArray();
		for (Map.Entry<Date, Double> entry : details.get(0).entrySet()) {
			JSONObject temp = new JSONObject();
			temp.put("label", entry.getKey());
			cat.put(temp);
		}
		obj.put("category", cat);
		array.put(obj);
		return array;

	}

	private JSONArray getdataset(List<Map<Date, Double>> details, String[] fields) throws JSONException {
		JSONArray array = new JSONArray();

		for (int i = 0; i < details.size(); i++) {
			JSONObject obj = new JSONObject();
			JSONArray cat = new JSONArray();
			for (Map.Entry<Date, Double> entry : details.get(i).entrySet()) {
				JSONObject temp = new JSONObject();
				temp.put("value", entry.getValue());
				cat.put(temp);
			}
			obj.put("seriesname", fields[i]);
			obj.put("data", cat);
			array.put(obj);
		}
		return array;

	}

	public JSONObject getGraphData(List<Map<Date, Double>> details, String[] fields) throws JSONException {
		JSONObject obj = new JSONObject();
		obj.put("categories", getCategories(details));
		obj.put("dataset", getdataset(details, fields));
		return obj;
	}

}
