package com.jpr.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jpr.app.domain.DimComponent;
import com.jpr.app.repository.DimComponentRepository;

@Service
public class ComponentService {

	@Autowired
	DimComponentRepository dimComponentRepo;

	public DimComponent createComponent(DimComponent compo) {
		return dimComponentRepo.save(compo);
	}

	public List<DimComponent> getComponents() {
		return dimComponentRepo.findAll();
	}

	public DimComponent getComponent(Integer id) {
		return dimComponentRepo.findOne(id);
	}

}
