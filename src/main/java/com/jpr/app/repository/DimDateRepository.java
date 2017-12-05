package com.jpr.app.repository;

import java.util.Date;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Temporal;

import com.jpr.app.domain.DimDate;

public interface DimDateRepository extends JpaRepository<DimDate, Integer> {
 DimDate findByDate(Date date);
}
