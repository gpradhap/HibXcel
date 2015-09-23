package com.gs.doc.vo;

import java.util.Date;
import java.util.List;

import com.gs.base.vo.BaseAbstractVo;
import com.gs.base.vo.User;
import com.gs.common.bo.AttributeValueBo;
import com.gs.common.builder.AttributeBuilder;
import com.gs.doc.bo.mapper.DocumentMapper;
import com.gs.emp.bo.EmployeeBo;
import com.gs.emp.vo.Employee;


public class Document extends BaseAbstractVo{

	private Long documentId;
	private  String docName;
	/*private  Date createdDate;
	private  Date updatedDate;
	private  String createdBy;
	private  String updatedBy;*/

	private Employee employee;
	
	public String getDocName() {
		return this.docName;
	}

	public Long getDocumentId() {
		return this.documentId;
	}

	public Employee getEmployee() {
		return this.employee;
	}
	
	/*public Date getCreatedDate() {
		return this.createdDate;
	}

	public Date getUpdatedDate() {
		return this.updatedDate;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public String getUpdatedBy() {
		return this.updatedBy;
	}*/
	
	/*public String toString(){
		return new StringBuilder().append(docName).append(createdDate)
				.append(createdBy).append(updatedDate).append(updatedBy)
				.toString();
	}*/

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}

	/*public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}*/

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Document() {
	}
}
