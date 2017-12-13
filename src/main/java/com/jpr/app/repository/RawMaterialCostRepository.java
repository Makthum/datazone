package com.jpr.app.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jpr.app.domain.RawMaterialCost;
import com.jpr.app.domain.RawMaterialCostPk;

public interface RawMaterialCostRepository extends JpaRepository<RawMaterialCost, RawMaterialCostPk> {

	List<RawMaterialCost> findByDimDateDate(Date date);

}
