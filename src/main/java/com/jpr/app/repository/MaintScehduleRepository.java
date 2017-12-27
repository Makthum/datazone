package com.jpr.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jpr.app.domain.MaintSchedule;

public interface MaintScehduleRepository extends JpaRepository<MaintSchedule, Integer> {

	List<MaintSchedule> findByTaskCreated(String val);

}
