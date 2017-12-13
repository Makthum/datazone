package com.jpr.app.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jpr.app.domain.FactScrapIssued;
import com.jpr.app.domain.ScrapRecvPk;

public interface FactScrapIssuedRepository extends JpaRepository<FactScrapIssued, ScrapRecvPk> {

	List<FactScrapIssued> findByDimDateDateBetween(Date fromDate, Date toDate, Pageable page);

	List<FactScrapIssued> findByDimDateDateBetween(Date fromDate, Date toDate);
	
	@Query(value = "select ifnull(sum(v.quantity),0) as total from factscrapissued v,dimdate d where v.dim_date_id=d.date_id and d.date < ? ", nativeQuery = true)
	BigDecimal findScrapIssued(Date tillDate);
	
	List<FactScrapIssued> findByDimDateDate(Date date);
}
