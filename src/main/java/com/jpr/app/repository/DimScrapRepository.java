package com.jpr.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jpr.app.domain.DimScrap;

public interface DimScrapRepository extends JpaRepository<DimScrap, Integer> {

	public DimScrap findFirstByOrderByIdDesc();
}
