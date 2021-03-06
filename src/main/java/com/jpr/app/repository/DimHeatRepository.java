package com.jpr.app.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jpr.app.domain.DimHeat;

public interface DimHeatRepository extends JpaRepository<DimHeat, Integer>{
	
	
	List<DimHeat> findByDimDateOnDateAfter(Date date);

	List<DimHeat> findByDimDateOnDateBetween(Date fromDate,Date toDate, Pageable page);
	

}
