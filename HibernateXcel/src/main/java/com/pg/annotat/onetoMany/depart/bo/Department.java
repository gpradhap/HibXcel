package com.pg.annotat.onetoMany.depart.bo;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name="department")
public class Department {
	
	@Id
	@Column(name="DEP_ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer departmentId;
	
	@Column(name="DEP_NAME")
	private String departmentName;

	@OneToMany(mappedBy="department")
	@Cascade({CascadeType.ALL})
	private Set<DepartStore> deptStores;
	
	
	public Department() {
	}	
	
	public Department(String departmentName) {
		this.departmentName = departmentName;
	}	

	
	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public Set<DepartStore> getDeptStores() {
		return deptStores;
	}

	public void setDeptStores(Set<DepartStore> deptStores) {
		this.deptStores = deptStores;
	}

}
