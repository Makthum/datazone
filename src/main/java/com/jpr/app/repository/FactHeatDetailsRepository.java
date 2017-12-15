package com.jpr.app.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.query.Param;

import com.jpr.app.domain.FactHeatDetails;

public interface FactHeatDetailsRepository extends JpaRepository<FactHeatDetails, Integer> {

	List<FactHeatDetails> findByDimHeatDimDateOnDateBetween(Date fromDate, Date toDate, Pageable page);

	@Query(value = "select sum(production) as production, sum(slag) as slag ,sum(delay) as BreakDown ,"
			+ "sum(unit_consumed) as ElectricityUnit," + "avg(power_factor) as PowerFactor,"
			+ "sum(time_taken) as RunningTime," + "avg(time_taken) as AverageRunningTime,"
			+ "avg(tapping_time) as AverageTappingTime," + "avg(unit_per_ton) as AvgUPT"
			+ " from factheatdetails  t inner join (select * from dimHeat h inner join dimdate d on d.date_id = h.dim_date_on_id where d.date = ? ) x on x.id = t.dim_heat_id", nativeQuery = true)
	List<Object[]> getDailyReport(String date);

}
