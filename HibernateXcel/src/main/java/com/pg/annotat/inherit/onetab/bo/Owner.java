package com.pg.annotat.inherit.onetab.bo;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table (name="person")
@DiscriminatorValue(value="Owner")
public class Owner extends Person{

	@Column(name="department_name")
	private String departmentName;
	
	public Owner() {
	}
	
	public Owner(String fName, String lName) {
		super(fName,lName);
	}

	public Owner(String fName, String lName, String depName) {
		super(fName,lName);
		this.departmentName = depName;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String toString(){
		return getPersonId()+" : "+getFirstName()+" : "+getLastName()+" : "+getDepartmentName();
	}
}
