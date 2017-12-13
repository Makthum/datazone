package com.jpr.app.service;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import com.jpr.app.domain.DimDate;
import com.jpr.app.domain.DimScrap;
import com.jpr.app.domain.FactScrapIssued;
import com.jpr.app.domain.FactScrapRecv;
import com.jpr.app.domain.ScrapRecvPk;
import com.jpr.app.repository.DimDateRepository;
import com.jpr.app.repository.DimScrapRepository;
import com.jpr.app.repository.FactScrapIssuedRepository;
import com.jpr.app.repository.FactScrapRecvRepository;
import com.jpr.app.service.dto.MonthlyOCBalanceDTO;
import com.jpr.app.service.dto.ScrapReceivedDTO;

@Service
@Transactional
public class ScrapService {

	@Autowired
	private DimScrapRepository dimScrapRepo;

	@Autowired
	private DimDateRepository dimDateRepo;

	@Autowired
	private FactScrapRecvRepository factScrapRecvRepo;

	@Autowired
	private FactScrapIssuedRepository factScrapIssuedRepo;

	@Autowired
	private JdbcTemplate template;

	public List<DimScrap> saveScrapType(List<DimScrap> scrap) {
		return dimScrapRepo.save(scrap);
	}

	public Integer getScrapMaxId() {
		DimScrap scrap = dimScrapRepo.findFirstByOrderByIdDesc();
		if (scrap == null)
			return 0;
		else
			return scrap.getId();
	}

	public List<DimScrap> getScrapTypes() {
		return dimScrapRepo.findAll();
	}

	public DimScrap getDimScrap(Integer id) {
		return dimScrapRepo.findOne(id);
	}

	public DimScrap saveScrapType(DimScrap scrap) {
		return dimScrapRepo.save(scrap);
	}

	public List<FactScrapRecv> saveScrapReceived(List<ScrapReceivedDTO> details) {
		Iterator<ScrapReceivedDTO> it = details.iterator();
		List<FactScrapRecv> bos = new ArrayList<FactScrapRecv>();
		while (it.hasNext()) {
			ScrapReceivedDTO dto = it.next();
			DimDate date = getDate(dto.getDate());
			if (date == null)
				throw new RuntimeException("Invalid Date");
			ScrapRecvPk pk = new ScrapRecvPk();
			pk.setDateId(date.getDate_id());
			pk.setScrapId(dto.getScrapType());

			DimScrap scrap = dimScrapRepo.getOne(dto.getScrapType());
			if (scrap == null)
				throw new RuntimeException("Invalid Scrap Id");

			FactScrapRecv temp = new FactScrapRecv();
			temp.setDimDate(date);
			temp.setDimScrap(scrap);
			temp.setId(pk);
			temp.setQuantity(dto.getQuantity());
			bos.add(temp);

		}
		return factScrapRecvRepo.save(bos);
	}

	public List<FactScrapIssued> saveScrapIssued(List<ScrapReceivedDTO> details) {
		Iterator<ScrapReceivedDTO> it = details.iterator();
		List<FactScrapIssued> bos = new ArrayList<FactScrapIssued>();
		while (it.hasNext()) {
			ScrapReceivedDTO dto = it.next();
			DimDate date = getDate(dto.getDate());
			if (date == null)
				throw new RuntimeException("Invalid Date");
			ScrapRecvPk pk = new ScrapRecvPk();
			pk.setDateId(date.getDate_id());
			pk.setScrapId(dto.getScrapType());

			DimScrap scrap = dimScrapRepo.getOne(dto.getScrapType());
			if (scrap == null)
				throw new RuntimeException("Invalid Scrap Id");

			FactScrapIssued temp = new FactScrapIssued();
			temp.setDimDate(date);
			temp.setDimScrap(scrap);
			temp.setId(pk);
			temp.setQuantity(dto.getQuantity());
			bos.add(temp);

		}
		return factScrapIssuedRepo.save(bos);
	}

	public DimDate getDate(Date date) {
		return dimDateRepo.findByDate(date);
	}

	public List<FactScrapRecv> getScrapReceivedLogs(Date fromDate, Date toDate, int page, int size) {
		Pageable pg = new PageRequest(page, size);
		return factScrapRecvRepo.findByDimDateDateBetween(fromDate, toDate, pg);

	}

	public List<FactScrapIssued> getScrapIssuedLogs(Date fromDate, Date toDate, int page, int size) {
		Pageable pg = new PageRequest(page, size);
		return factScrapIssuedRepo.findByDimDateDateBetween(fromDate, toDate, pg);

	}

	public List<Map<String, Object>> getScrapReceivedReport(Date fromDate, Date toDate, int page, int size) {
		template.setResultsMapCaseInsensitive(true);
		SimpleJdbcCall call = new SimpleJdbcCall(template).withProcedureName("scrap_received")
				.withoutProcedureColumnMetaDataAccess().useInParameterNames("in_id")
				.declareParameters(new SqlParameter("fromDate", Types.DATE), new SqlParameter("toDate", Types.DATE));
		SqlParameterSource in = new MapSqlParameterSource().addValue("fromDate", fromDate, Types.DATE)
				.addValue("toDate", toDate, Types.DATE);
		Map<String, Object> result = call.execute(in);
		for (Map.Entry<String, Object> entry : result.entrySet()) {
			return (List<Map<String, Object>>) entry.getValue();
		}
		return null;

	}

	public List<Map<String, Object>> getScrapIssuedReport(Date fromDate, Date toDate, int page, int size) {
		template.setResultsMapCaseInsensitive(true);
		SimpleJdbcCall call = new SimpleJdbcCall(template).withProcedureName("scrap_issued")
				.withoutProcedureColumnMetaDataAccess().useInParameterNames("in_id")
				.declareParameters(new SqlParameter("fromDate", Types.DATE), new SqlParameter("toDate", Types.DATE));
		SqlParameterSource in = new MapSqlParameterSource().addValue("fromDate", fromDate, Types.DATE)
				.addValue("toDate", toDate, Types.DATE);
		Map<String, Object> result = call.execute(in);
		for (Map.Entry<String, Object> entry : result.entrySet()) {
			return (List<Map<String, Object>>) entry.getValue();
		}
		return null;

	}

	public List<MonthlyOCBalanceDTO> getMonthlyBalance(Date fromDate, Date toDate) {
		List<FactScrapRecv> scrapReceived = factScrapRecvRepo.findByDimDateDateBetween(fromDate, toDate);
		List<FactScrapIssued> scrapIssued = factScrapIssuedRepo.findByDimDateDateBetween(fromDate, toDate);
		List<DimDate> dates = dimDateRepo.findByDateBetween(fromDate, toDate);
		Integer totalRecv = (Integer) factScrapRecvRepo.findScrapReceived(fromDate).toBigInteger().intValue();
		Integer totalIssued = (Integer) factScrapIssuedRepo.findScrapIssued(fromDate).toBigInteger().intValue();
		int opBal = totalRecv - totalIssued;
		int clBal = 0;
		List<MonthlyOCBalanceDTO> result = new ArrayList<>();
		for (DimDate d : dates) {
			MonthlyOCBalanceDTO dto = new MonthlyOCBalanceDTO();
			dto.setDate(d.getDate());
			dto.setOpBalance(opBal);
			dto.setScrapReceived(getEntry(scrapReceived, d));
			dto.setScrapIssued(getObject(scrapIssued, d));
			dto.setClBalance(dto.getOpBalance() + dto.getScrapReceived() - dto.getScrapIssued());
			opBal = dto.getOpBalance() + dto.getScrapReceived() - dto.getScrapIssued();
			result.add(dto);
		}
		return result;
	}

	private int getEntry(List<FactScrapRecv> details, DimDate date) {
		int result = 0;
		for (FactScrapRecv entry : details) {
			if (entry.getDimDate().getDate().compareTo(date.getDate()) == 0) {
				result = result + entry.getQuantity();
			}
		}
		return result;
	}

	private int getObject(List<FactScrapIssued> details, DimDate date) {
		int result = 0;
		for (FactScrapIssued entry : details) {
			if (entry.getDimDate().getDate().compareTo(date.getDate()) == 0) {
				result = result + entry.getQuantity();
			}
		}
		return result;
	}

	public Map<String, Double> getComposition(Date date) {
		List<FactScrapIssued> issued = factScrapIssuedRepo.findByDimDateDate(date);
		HashMap<String, Double> result = new HashMap<>();
		int total = 0;
		for (FactScrapIssued scrap : issued) {
			total += scrap.getQuantity();
			result.put(scrap.getDimScrap().getName(), new Double(scrap.getQuantity()));
		}

		for (Map.Entry<String, Double> entry : result.entrySet()) {
			entry.setValue(entry.getValue() / total);
		}

		return result;

	}

	public Map<DimScrap, Double> getCompositionByScrap(Date date) {
		List<FactScrapIssued> issued = factScrapIssuedRepo.findByDimDateDate(date);
		HashMap<DimScrap, Double> result = new HashMap<>();
		for (FactScrapIssued scrap : issued) {
			result.put(scrap.getDimScrap(), new Double(scrap.getQuantity()));
		}
		return result;

	}

}
