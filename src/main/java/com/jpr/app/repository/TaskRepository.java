package com.jpr.app.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jpr.app.domain.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {

	@Query(value = "select * from task where start_date >= ? and end_date <=?  and work_type='maintenance'", nativeQuery = true)
	List<Task> getBatchTasks(Date sDate, Date eDate);
	
	List<Task> findByStartDateBetween(Date fromDate,Date toDate);
	
	List<Task> findByStatus(String status);

}
