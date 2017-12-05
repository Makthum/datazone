package com.jpr.app.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jpr.app.domain.FactScrapIssued;
import com.jpr.app.domain.ScrapRecvPk;

public interface FactScrapIssuedRepository extends JpaRepository<FactScrapIssued, ScrapRecvPk> {

	List<FactScrapIssued> findByDimDateDateBetween(Date fromDate, Date toDate, Pageable page);
}
