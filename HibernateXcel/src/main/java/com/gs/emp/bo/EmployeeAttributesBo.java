package com.gs.emp.bo;

import java.io.Serializable;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.gs.base.bo.BaseAbstractBo;
import com.gs.common.bo.AttributeLookupBo;
import com.pg.annotat.manytomany.bi.extracolumn.StockCategoryExtn;

@Entity
@Table(name="employeeattributes")
@AssociationOverrides({
	@AssociationOverride(name="employeeAttributesBoID.employee",joinColumns=@JoinColumn(name="employeeId")),
	@AssociationOverride(name="employeeAttributesBoID.attributeLookup",joinColumns=@JoinColumn(name="attrLookupID"))
	//@AssociationOverride(name="employeeattributes.attrLookupGrpSet",joinColumns=@JoinColumn(name="attrLookupGrpID"))
	})
public class EmployeeAttributesBo extends BaseAbstractBo implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EmployeeAttributesBoID employeeAttributesBoID = new EmployeeAttributesBoID();
	private String attributeName;
	private String attributeDesc;
	private String attributeValue;
	
	private EmployeeBo employee;
	private AttributeLookupBo attributeLookup;
	
	@EmbeddedId
	public EmployeeAttributesBoID getEmployeeAttributesBoID() {
		return employeeAttributesBoID;
	}

	@Column
	public String getAttributeDesc() {
		return attributeDesc;
	}

	/*@Transient
	public EmployeeBo getEmployeeSet() {
		return employeeAttributesBoID.getEmployee();
	}

	@Transient
	public AttributeLookupBo getAttributeLookupSet() {
		return employeeAttributesBoID.getAttributeLookup();
	}*/

	@Column
	public String getAttributeValue() {
		return attributeValue;
	}

	@Transient
	public EmployeeBo getEmployee() {
		return employeeAttributesBoID.getEmployee();
	}

	@Transient
	public AttributeLookupBo getAttributeLookup() {
		return employeeAttributesBoID.getAttributeLookup();
	}
	
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}	

	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}

	public void setEmployee(EmployeeBo employee) {
		//this.employee = employee;
		employeeAttributesBoID.setEmployee(employee);
	}

	public void setAttributeLookup(AttributeLookupBo attributeLookup) {
		//this.attributeLookup = attributeLookup;
		employeeAttributesBoID.setAttributeLookup(attributeLookup);
	}
	
	public void setAttributeDesc(String attributeDesc) {
		this.attributeDesc = attributeDesc;
	}
	public void setEmployeeAttributesBoID(EmployeeAttributesBoID employeeAttributesBoID) {
		this.employeeAttributesBoID = employeeAttributesBoID;
	}
	
	
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;
 
		EmployeeAttributesBo otherObj = (EmployeeAttributesBo) object;
 
		if (getEmployeeAttributesBoID() != null ? !getEmployeeAttributesBoID().equals(otherObj.getEmployeeAttributesBoID())
				: otherObj.getEmployeeAttributesBoID() != null){
			return false;
		}
 
		return true;
	}

}
