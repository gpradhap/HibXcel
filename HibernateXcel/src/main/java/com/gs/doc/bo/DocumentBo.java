package com.gs.doc.bo;

import java.util.Date;
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

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.gs.base.bo.BaseAbstractBo;
import com.gs.common.util.JavaUtilDateType;
import com.gs.emp.bo.EmployeeBo;

@Entity
@Table (name="document")
@TypeDefs({ @TypeDef(name = "javaUtilDateType", defaultForType = java.util.Date.class, typeClass = JavaUtilDateType.class) })
public class DocumentBo extends BaseAbstractBo{

	private  Long documentId;
	private  String docName;
	private  Date createdDate;
	private  Date updatedDate;
	/*private  String createdBy;
	private  String updatedBy;*/

	private EmployeeBo employee;
	private Set<DocumentAttributesBo> documentAttributes = new HashSet<DocumentAttributesBo>();
	
	public DocumentBo() {
	}
	
	@Id
	@Column(name="DocumentID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getDocumentId() {
		return this.documentId;
	}

	@Column
	public String getDocName() {
		return this.docName;
	}

	@Column(nullable=true)
	@Type(type="javaUtilDateType")
	public Date getCreatedDate() {
		return this.createdDate;
	}
	
	/*@Column(nullable=true)
	public String getCreatedBy() {
		return createdBy;
	}*/

	@Column(nullable=true)
	@Type(type="javaUtilDateType")
	public Date getUpdatedDate() {
		return this.updatedDate;
	}

	/*@Column(nullable=true)
	public String getUpdatedBy() {
		return this.updatedBy;
	}*/

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="emp_id",referencedColumnName="employeeId")
	public EmployeeBo getEmployee() {
		return this.employee;
	}

	@OneToMany(mappedBy="documentBo",fetch=FetchType.LAZY,cascade={CascadeType.ALL})
	public Set<DocumentAttributesBo> getDocumentAttributes() {
		return documentAttributes;
	}
	
	
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	/*public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}*/

	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public void setEmployee(EmployeeBo employee) {
		this.employee = employee;
	}
	
	public Date setCreatedDate(Date createdDate) {
		return this.createdDate = createdDate;
	}

	/*public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}*/

	public void setDocumentAttributes(Set<DocumentAttributesBo> documentAttributes) {
		this.documentAttributes = documentAttributes;
	}
}
