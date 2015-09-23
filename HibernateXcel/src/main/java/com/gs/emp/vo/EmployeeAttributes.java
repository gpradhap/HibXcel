package com.gs.emp.vo;

import com.gs.base.vo.BaseAbstractVo;
import com.gs.common.vo.AttributeLookup;

public class EmployeeAttributes extends BaseAbstractVo {

	private String attributeName;
	private String attributeDesc;
	private String attributeValue;
	
	private Employee employee;
	private AttributeLookup attributeLookup;

	public String getAttributeDesc() {
		return attributeDesc;
	}

	public void setAttributeDesc(String attributeDesc) {
		this.attributeDesc = attributeDesc;
	}

	public String getAttributeValue() {
		return attributeValue;
	}

	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}

	public Employee getEmployee() {
		return employee;
	}

	public AttributeLookup getAttributeLookup() {
		return attributeLookup;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public void setAttributeLookup(AttributeLookup attributeLookup) {
		this.attributeLookup = attributeLookup;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}


}
