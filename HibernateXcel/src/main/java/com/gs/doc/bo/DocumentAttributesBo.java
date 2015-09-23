package com.gs.doc.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.gs.base.bo.BaseAbstractBo;
import com.gs.common.bo.AttributeLookupBo;

@Entity(name="documentattributes")
@Table(name="DocumentAttributes")
public class DocumentAttributesBo extends BaseAbstractBo{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="DocAttributeID")
	private Long docAttributeId;

	@Column(name="AttributeDesc")
	private String attributeDescription;
	
	@Column(name="AttributeVal1")
	private String attributeValue1;

	@Column(name="AttributeVal2")
	private String attributeValue2;

	@ManyToOne
	@JoinColumn(name="DocumentID")
	private DocumentBo documentBo;
	
	@ManyToOne
	@JoinColumn(name="AttrLookupID")
	private AttributeLookupBo attributeLookupBo;
	
	public DocumentAttributesBo() {
	}

	public DocumentAttributesBo(String attributeValue1) {
		this.attributeValue1 = attributeValue1;
	}

	public DocumentAttributesBo(String attributeDesc, String attributeValue1) {
		this.attributeValue1 = attributeValue1;
		this.attributeDescription = attributeDesc;
	}

	public Long getDocAttributeId() {
		return docAttributeId;
	}

	public String getAttributeDescription() {
		return attributeDescription;
	}

	public String getAttributeValue1() {
		return attributeValue1;
	}

	public String getAttributeValue2() {
		return attributeValue2;
	}

	public DocumentBo getDocumentBo() {
		return documentBo;
	}

	public AttributeLookupBo getAttributeLookupBo() {
		return attributeLookupBo;
	}

	public void setDocAttributeId(Long docAttributeId) {
		this.docAttributeId = docAttributeId;
	}

	public void setAttributeDescription(String attributeDescription) {
		this.attributeDescription = attributeDescription;
	}

	public void setAttributeValue1(String attributeValue1) {
		this.attributeValue1 = attributeValue1;
	}

	public void setAttributeValue2(String attributeValue2) {
		this.attributeValue2 = attributeValue2;
	}

	public void setDocumentBo(DocumentBo documentBo) {
		this.documentBo = documentBo;
	}

	public void setAttributeLookupBo(AttributeLookupBo attributeLookupBo) {
		this.attributeLookupBo = attributeLookupBo;
	}

}
