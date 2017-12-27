package com.jpr.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jpr.app.domain.Personnel;

public interface PersonnelRepository extends JpaRepository<Personnel, Integer> {

}
