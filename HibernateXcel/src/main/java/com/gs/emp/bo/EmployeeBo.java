package com.gs.emp.bo;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.gs.base.bo.BaseAbstractBo;
import com.gs.common.util.JavaUtilDateType;
import com.gs.doc.bo.DocumentBo;

@Entity
@Table(name="employee")
@TypeDefs({ @TypeDef(name = "javaUtilDateType", defaultForType = java.util.Date.class, typeClass = JavaUtilDateType.class) })
public class EmployeeBo extends BaseAbstractBo implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer employeeId;
	private String firstName;
	private String lastName;
	private String onlineUserId;
	private Date dateOfBirth;
	private List<DocumentBo> documents;
	
	private Set<EmployeeAttributesBo> empAttributeSet = new HashSet<EmployeeAttributesBo>(0);
	
	public EmployeeBo() {
	}

	public EmployeeBo(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public EmployeeBo(String onlineUserId) {
		this.onlineUserId = onlineUserId;
	}

	public EmployeeBo(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	@Id
	@Column(name="employeeId")
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getEmployeeId() {
		return employeeId;
	}

	@Column
	public String getFirstName() {
		return firstName;
	}

	@Column
	public String getLastName() {
		return lastName;
	}

	@Column
	public String getOnlineUserId() {
		return onlineUserId;
	}

	@Column
	@Type(type="javaUtilDateType")
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	
	@OneToMany(mappedBy="employee",fetch=FetchType.LAZY)
	@Cascade(value={CascadeType.ALL})
	public List<DocumentBo> getDocuments() {
		return documents;
	}

	@OneToMany(fetch=FetchType.LAZY,cascade=CascadeType.ALL,mappedBy="employeeAttributesBoID.employee")
	//@Cascade(value={CascadeType.ALL})
	public Set<EmployeeAttributesBo> getEmpAttributeSet() {
		return empAttributeSet;
	}


	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
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

	public void setDocuments(List<DocumentBo> documents) {
		this.documents = documents;
	}

	public void setEmpAttributeSet(Set<EmployeeAttributesBo> empAttributeSet) {
		this.empAttributeSet = empAttributeSet;
	}
	
}
