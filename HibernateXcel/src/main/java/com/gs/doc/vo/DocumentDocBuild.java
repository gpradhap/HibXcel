package com.gs.doc.vo;

import java.util.Date;
import java.util.List;

import com.gs.common.bo.AttributeValueBo;
import com.gs.common.builder.AttributeBuilder;
import com.gs.doc.bo.mapper.DocumentMapper;
import com.gs.emp.bo.EmployeeBo;
import com.gs.emp.vo.Employee;


public class DocumentDocBuild{

	private final String docName;
	private final Long documentId; 
	private final Date createdDate;
	private final Date updatedDate;
	private final String createdBy;
	private final String updatedBy;
	
	private final Employee employee;
	

	private DocumentDocBuild(DocumentBuilder documentBuilder) {
		this.docName = documentBuilder.docName;
		this.createdDate = documentBuilder.createdDate;
		this.createdBy = documentBuilder.createdBy;
		this.updatedDate = documentBuilder.updatedDate;
		this.updatedBy = documentBuilder.updatedBy;
		this.documentId = documentBuilder.documentId;
		this.employee = documentBuilder.employee;
	}
	public String getDocName() {
		return docName;
	}

	public Long getDocumentId() {
		return this.documentId;
	}

	public Employee getEmployee() {
		return this.employee;
	}
	
	public Date getCreatedDate() {
		return createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}
	
	public String toString(){
		return new StringBuilder().append(docName).append(createdDate)
				.append(createdBy).append(updatedDate).append(updatedBy)
				.toString();
	}

	public static class DocumentBuilder{

		private final String docName;
		private final Date createdDate;
		private final String createdBy;

		private Long documentId; 

		private Date updatedDate;
		private String updatedBy;
		
		private Employee employee;
		
		/*private AttributeValueBo isAvailable;
		private AttributeValueBo viewPermission;
		private AttributeValueBo editPermission;
		private AttributeValueBo deletePermission;
		private AttributeValueBo url;*/
		
		public DocumentBuilder(String docName,Date createdDate, String createdBy) {
			this.docName = docName;
			this.createdDate = createdDate;
			this.createdBy = createdBy;
		}
		
		public DocumentBuilder setUpdatedBy(String updatedBy){
			this.updatedBy = updatedBy;
			return this;
		}

		public DocumentBuilder setUpdatedDate(Date updatedDate){
			this.updatedDate = updatedDate;
			return this;
		}

		public void setEmployee(Employee employee) {
			this.employee = employee;
		}		

		public void setDocumentId(Long documentId) {
			this.documentId = documentId;
		}		

		
		/*@Override
		public DocumentBuilder populateAttributeValues(List<AttributeValueBo> attrValueList) {
			if(null == attrValueList || attrValueList.size() ==0){
				return null;
			}
			for(AttributeValueBo attrValue:attrValueList){
				super.addAttributeValue(attrValue);
			}
			return this;
		}*/

		public DocumentDocBuild build() {
			return new DocumentDocBuild(this);
		}

	}
}
