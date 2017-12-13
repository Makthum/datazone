package com.jpr.app.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.jpr.app.domain.DimIssue;
import com.jpr.app.repository.DimIssueRepository;

@Service
public class IssueService {

	@Autowired
	DimIssueRepository issueRepo;

	public DimIssue createDimIssue(DimIssue issue) {
		return issueRepo.save(issue);
	}

	public List<DimIssue> getIssues(Date fromDate, Date toDate, int page, int size) {
		return issueRepo.findByDimDateDateBetween(fromDate, toDate, new PageRequest(page, size));
	}

	public List<DimIssue> getIssuesLastThreeDays() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -3);
		Date date = cal.getTime();
		return issueRepo.findByDimDateDateAfter(date);
	}
	
	public DimIssue getIssue(Integer id) {
		return issueRepo.getOne(id);
	}
}
