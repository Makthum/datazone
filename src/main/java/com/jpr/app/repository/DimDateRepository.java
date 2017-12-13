package com.jpr.app.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jpr.app.domain.DimDate;

public interface DimDateRepository extends JpaRepository<DimDate, Integer> {
	DimDate findByDate(Date date);

	List<DimDate> findByDateBetween(Date fromDate, Date toDate);
}
