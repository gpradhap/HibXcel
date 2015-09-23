package com.pg.annotat.inherit.onetab.bo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="person")
@DiscriminatorValue(value="Employee")
public class Employee extends Person{

	@Column(name="joining_date")
	private Date joiningDate;
	
	@Column(name="department_name")
	private String departmentName;

	public Employee() {
		// TODO Auto-generated constructor stub
	}
	
	public Employee(Date joinDate, String depName) {
		this.joiningDate = joinDate;
		this.departmentName = depName;
	}
	
	public Employee(String fName, String lName, Date joinDate, String depName) {
		super(fName,lName);
		this.joiningDate = joinDate;
		this.departmentName = depName;
	}

	
	public Date getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(Date joiningDate) {
		this.joiningDate = joiningDate;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String toString(){
		return getPersonId()+" : "+getFirstName()+" : "+getLastName()+" : "+getJoiningDate()+" : "+getDepartmentName();
	}
}
