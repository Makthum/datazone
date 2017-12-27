package com.jpr.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jpr.app.domain.Equipment;

public interface EquipmentRepository extends JpaRepository<Equipment, Integer> {

}
