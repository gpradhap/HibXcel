package com.gs.emp.bo.pojo;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.gs.common.bo.AttrLookupGroupBo;

@XmlRootElement(name = "AttrLookupGroupList")
@XmlAccessorType(XmlAccessType.FIELD)
public class AttrLookupGroupPojo {
	@XmlElement(name = "AttrLookupGroup")
	List<AttrLookupGroupBo> attrLookupGroupList = null;

	public AttrLookupGroupPojo() {
	}

	public AttrLookupGroupPojo(List<AttrLookupGroupBo> attrLookupGroupList) {
		this.attrLookupGroupList = attrLookupGroupList;
	}

	public List<AttrLookupGroupBo> getAttrLookupGroupList() {
		return attrLookupGroupList;
	}

	public void setAttrLookupGroupList(List<AttrLookupGroupBo> attrLookupGroupList) {
		this.attrLookupGroupList = attrLookupGroupList;
	}

}
