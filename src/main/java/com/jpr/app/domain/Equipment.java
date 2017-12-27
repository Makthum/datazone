package com.jpr.app.domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "equipment")
public class Equipment {
	
	@OneToMany(mappedBy="parent")
    private Collection<Equipment> children;
	private Date dateCommissioned;
	private String department;
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String name;
	@ManyToOne
    private Equipment parent;
	private String type;
	private String vendorContactNo;
    private String vendorName;
	public Collection<Equipment> getChildren() {
		return children;
	}
	public Date getDateCommissioned() {
		return dateCommissioned;
	}
	public String getDepartment() {
		return department;
	}
	public Integer getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public Equipment getParent() {
		return parent;
	}
	public String getType() {
		return type;
	}
	public String getVendorContactNo() {
		return vendorContactNo;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setChildren(Collection<Equipment> children) {
		this.children = children;
	}
	public void setDateCommissioned(Date dateCommissioned) {
		this.dateCommissioned = dateCommissioned;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setParent(Equipment parent) {
		this.parent = parent;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setVendorContactNo(String vendorContactNo) {
		this.vendorContactNo = vendorContactNo;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
    
    

}
