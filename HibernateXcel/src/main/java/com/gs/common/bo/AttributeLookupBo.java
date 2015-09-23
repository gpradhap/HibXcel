package com.gs.common.bo;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlType;

import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.gs.base.bo.BaseAbstractBo;
import com.gs.common.util.JavaUtilDateType;
import com.gs.emp.bo.EmployeeAttributesBo;

@Entity
@Table(name="AttributeLookup")
@XmlType(propOrder={"attrLookupID","attribute","attrLookupGroup"})
@TypeDefs({ @TypeDef(name = "javaUtilDateType", defaultForType = java.util.Date.class, typeClass = JavaUtilDateType.class) })
public class AttributeLookupBo extends BaseAbstractBo implements Serializable {

	//static Logger log = Logger.getLogger(AttributeLookupBo.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer attrLookupID;
	private String attribute;
	private String attrDesc;//attr_desc
	private Integer displayOrder;//display_order
	private AttrLookupGroupBo attrLookupGroup;

	
	private Set<EmployeeAttributesBo> empAttributeSet = new HashSet<EmployeeAttributesBo>(0);

	@OneToMany(fetch=FetchType.LAZY,cascade=CascadeType.ALL,mappedBy="employeeAttributesBoID.attributeLookup")
	public Set<EmployeeAttributesBo> getEmpAttributeSet() {
		return empAttributeSet;
	}
	public void setEmpAttributeSet(Set<EmployeeAttributesBo> empAttributeSet) {
		this.empAttributeSet = empAttributeSet;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) //
	@Column(name="AttrLookupID")
	public Integer getAttrLookupID() {
		return attrLookupID;
	}

	@Column(name="Attribute")
	public String getAttribute() {
		return attribute;
	}

	@ManyToOne(cascade={javax.persistence.CascadeType.ALL},fetch=FetchType.EAGER)
	//@Cascade({CascadeType.ALL})
	@JoinColumn(name="AttrGroupID")
	public AttrLookupGroupBo getAttrLookupGroup() {
		return attrLookupGroup;
	}

	public void setAttrLookupGroup(AttrLookupGroupBo attrLookupGroup) {
		this.attrLookupGroup = attrLookupGroup;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public void setAttrLookupID(Integer attrLookupID) {
		this.attrLookupID = attrLookupID;
	}
	
	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}
	public void setAttrDesc(String attrDesc) {
		this.attrDesc = attrDesc;
	}
	
	/*@Column(name="update_dt")
	@Type(type="javaUtilDateType")
	public Date getUpdateDate() {
		if(null == updateDate){
			updateDate = super.getUpdateDate();
		}		
		return updateDate;
	}

	@Column(name="expire_dt")
	@Type(type="javaUtilDateType")
	public Date getExpireDate() {
		if(null == expireDate){
			expireDate = super.getExpireDate();
		}		
		return expireDate;
	}

	@Column(name="update_by")
	public String getUpdateBy() {
		if(null == expireDate){
			expireDate = super.getExpireDate();
		}		
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
