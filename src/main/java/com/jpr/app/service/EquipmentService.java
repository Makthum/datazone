package com.jpr.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jpr.app.domain.Equipment;
import com.jpr.app.repository.EquipmentRepository;
import com.jpr.app.service.dto.EquipmentDTO;

@Service
public class EquipmentService {

	@Autowired
	EquipmentRepository eRepo;

	public Equipment createEquipment(EquipmentDTO e) {
		Equipment temp = null;
		Equipment parent = null;
		if (e.getId() != null) {
			temp = eRepo.getOne(e.getId());
		}
		if (e.getParentId() != null) {
			parent = eRepo.findOne(e.getParentId());
		}
		if (temp == null) {
			temp = new Equipment();
		}
		temp.setDateCommissioned(e.getDateCommissioned());
		temp.setDepartment(e.getDepartment());
		temp.setName(e.getName());
		temp.setParent(parent);
		temp.setType(e.getType());
		temp.setVendorContactNo(e.getVendorContactNo());
		temp.setVendorName(e.getVendorName());
		return eRepo.save(temp);
	}

	public List<Equipment> getAll() {
		return eRepo.findAll();
	}

}
