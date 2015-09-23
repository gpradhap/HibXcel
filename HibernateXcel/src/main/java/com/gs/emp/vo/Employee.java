package com.gs.emp.vo;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.gs.base.vo.User;
import com.gs.doc.vo.Document;
import com.gs.emp.bo.EmployeeAttributesBo;

public class Employee extends User {

	private Integer employeeId;
	private String firstName;
	private String lastName;
	private String onlineUserId;
	private Date dateOfBirth;
	private Set<EmployeeAttributes> empAttributeSet = new HashSet<EmployeeAttributes>();
	private List<Document> documents;
	
	public Employee() {
	}
	
	public Employee(Integer employeeId) {
		this.employeeId = employeeId;
	}


	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getOnlineUserId() {
		return onlineUserId;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setOnlineUserId(String onlineUserId) {
		this.onlineUserId = onlineUserId;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public List<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}

	public Set<EmployeeAttributes> getEmpAttributeSet() {
		return empAttributeSet;
	}

	public void setEmpAttributeSet(Set<EmployeeAttributes> empAttributeSet) {
		this.empAttributeSet = empAttributeSet;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

}
