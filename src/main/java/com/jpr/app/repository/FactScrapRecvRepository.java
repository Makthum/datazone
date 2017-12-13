package com.jpr.app.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jpr.app.domain.FactScrapRecv;
import com.jpr.app.domain.ScrapRecvPk;

public interface FactScrapRecvRepository extends JpaRepository<FactScrapRecv, ScrapRecvPk> {

	List<FactScrapRecv> findByDimDateDateBetween(Date fromDate, Date toDate, Pageable page);

	List<FactScrapRecv> findByDimDateDateBetween(Date fromDate, Date toDate);

	@Query(value = "select ifnull(sum(v.quantity),0) as total from factscraprecv v,dimdate d where v.dim_date_id=d.date_id and d.date < ? ", nativeQuery = true)
	BigDecimal findScrapReceived(Date tillDate);
}
