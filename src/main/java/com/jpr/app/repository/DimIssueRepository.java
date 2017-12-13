package com.jpr.app.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jpr.app.domain.DimIssue;

public interface DimIssueRepository extends JpaRepository<DimIssue, Integer> {

	List<DimIssue> findByDimDateDateBetween(Date fromDate, Date toDate, Pageable page);

	List<DimIssue> findByDimDateDateAfter(Date date);
}
