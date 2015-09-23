package com.gs.common.bo;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.gs.base.bo.BaseAbstractBo;
import com.gs.common.util.JavaUtilDateType;

@Entity
@Table(name="AttrLookupGroup")
@TypeDefs({ @TypeDef(name = "javaUtilDateType", defaultForType = java.util.Date.class, typeClass = JavaUtilDateType.class) })
//@XmlAccessorType(XmlAccessType.FIELD)
public class AttrLookupGroupBo extends BaseAbstractBo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer attrLookupGrpID;
	private String groupName;
	
	/*private Date updateDate;
	private Date expireDate;
	private String updateBy;*/	
	
	private Set<AttributeLookupBo> attributeLookup; 
	
	public AttrLookupGroupBo() {}
	
	public AttrLookupGroupBo(String groupName) {
		this.groupName = groupName;
	}
	public AttrLookupGroupBo(Integer attrLookupGrpID) {
		this.attrLookupGrpID = attrLookupGrpID;
	}
	
	//@XmlElement
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	//@GeneratedValue(strategy=GenerationType.AUTO)
	//@Column(name="AttrLookupGrpID",nullable=false)
	@Column(name="AttrLookupGrpID")
	public Integer getAttrLookupGrpID() {
		return attrLookupGrpID;
	}

	//@XmlElement
	//@Column(name="GroupName",nullable=false)
	@Column(name="GroupName")
	public String getGroupName() {
		return groupName;
	}

	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER,mappedBy="attrLookupGroup")
	public Set<AttributeLookupBo> getAttributeLookup() {
		return attributeLookup;
	}

	/*
	//private Set<EmployeeAttributesBo> employeeAttrBoSet = new HashSet<EmployeeAttributesBo>();

	@OneToMany(fetch=FetchType.LAZY,mappedBy="employeeattributes.attrLookupGrpSet")
	public Set<EmployeeAttributesBo> getEmployeeAttrBoSet() {
		return employeeAttrBoSet;
	}
	public void setEmployeeAttrBoSet(Set<EmployeeAttributesBo> employeeAttrBoSet) {
		this.employeeAttrBoSet = employeeAttrBoSet;
	}*/
	
	
	
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	public void setAttributeLookup(Set<AttributeLookupBo> attributeLookup) {
		this.attributeLookup = attributeLookup;
	}
	
	public void setAttrLookupGrpID(Integer attrLookupGrpID) {
		this.attrLookupGrpID = attrLookupGrpID;
	}

	/*@Column(name="update_dt")
	@Type(type="javaUtilDateType")
	public Date getUpdateDate() {
		return updateDate;
	}

	@Column(name="expire_dt")
	@Type(type="javaUtilDateType")
	public Date getExpireDate() {
		return expireDate;
	}

	@Column(name="update_by")
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

}
