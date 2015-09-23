package com.gs.common.vo;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;

import com.gs.base.vo.BaseAbstractVo;
import com.gs.emp.vo.EmployeeAttributes;

/*public class AttributeLookup extends User{*/
public class AttributeLookup extends BaseAbstractVo{
	private Integer attrLookupID;
	private String attribute;
	private String attrDesc;
	private Integer displayOrder;	
	private AttrLookupGroup attrLookupGroup;
	
	private Set<EmployeeAttributes> empAttributeSet = new LinkedHashSet<EmployeeAttributes>();
	
	/*private Date updateDate;
	private Date expireDate;
	private String updateBy;*/
	
	public Integer getAttrLookupID() {
		return attrLookupID;
	}
	public String getAttribute() {
		return attribute;
	}
	public AttrLookupGroup getAttrLookupGroup() {
		return attrLookupGroup;
	}
	public void setAttrLookupID(Integer attrLookupID) {
		this.attrLookupID = attrLookupID;
	}
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	public void setAttrLookupGroup(AttrLookupGroup attrLookupGroup) {
		this.attrLookupGroup = attrLookupGroup;
	}
	/*public Date getUpdateDate() {
		return updateDate;
	}
	public Date getExpireDate() {
		return expireDate;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}*/
	public Set<EmployeeAttributes> getEmpAttributeSet() {
		return empAttributeSet;
	}
	public void setEmpAttributeSet(Set<EmployeeAttributes> empAttributeSet) {
		this.empAttributeSet = empAttributeSet;
	}
	public Integer getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}
	
	public void setAttrDesc(String attrDesc) {
		this.attrDesc = attrDesc;
	}

}
