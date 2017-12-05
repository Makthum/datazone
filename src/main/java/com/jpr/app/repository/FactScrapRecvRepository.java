package com.jpr.app.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jpr.app.domain.FactScrapRecv;
import com.jpr.app.domain.ScrapRecvPk;

public interface FactScrapRecvRepository extends JpaRepository<FactScrapRecv, ScrapRecvPk> {

	List<FactScrapRecv> findByDimDateDateBetween(Date fromDate,Date toDate,Pageable page);
}
