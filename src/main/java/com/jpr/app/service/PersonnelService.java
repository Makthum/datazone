package com.jpr.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jpr.app.domain.Personnel;
import com.jpr.app.repository.PersonnelRepository;

@Service
public class PersonnelService {
	
	@Autowired
	PersonnelRepository pRepo;
	
	public Personnel createPersonnel(Personnel p) {
		return pRepo.save(p);
	}
	
	public List<Personnel> getAll(){
		return pRepo.findAll();
	}

}
