package com.jpr.app.service.dto;

import java.util.Date;

public class EquipmentDTO {

	private Date dateCommissioned;
	private String department;
	private Integer id;
	private String name;
	private Integer parentId;
	private String type;
	private String vendorContactNo;
	private String vendorName;

	public Date getDateCommissioned() {
		return dateCommissioned;
	}

	public void setDateCommissioned(Date dateCommissioned) {
		this.dateCommissioned = dateCommissioned;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getVendorContactNo() {
		return vendorContactNo;
	}

	public void setVendorContactNo(String vendorContactNo) {
		this.vendorContactNo = vendorContactNo;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

}
