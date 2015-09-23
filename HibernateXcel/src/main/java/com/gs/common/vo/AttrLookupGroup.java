package com.gs.common.vo;

import java.util.Set;

import com.gs.base.vo.BaseAbstractVo;

/*public class AttrLookupGroup extends User{*/
public class AttrLookupGroup extends BaseAbstractVo{
	private Integer attrLookupGrpID;
	private String groupName;

	private Set<AttributeLookup> attributeLookup;

	public AttrLookupGroup() {}
	
	public AttrLookupGroup(String groupName) {
		this.groupName = groupName;
	}

	public AttrLookupGroup(Integer attrLookupGrpId) {
		this.attrLookupGrpID = attrLookupGrpId;
	}

	public AttrLookupGroup(Integer attrLookupGrpId, String groupName) {
		this.groupName = groupName;
		this.attrLookupGrpID = attrLookupGrpId;
	}
	
	public Integer getAttrLookupGrpID() {
		return attrLookupGrpID;
	}
	public String getGroupName() {
		return groupName;
	}
	public Set<AttributeLookup> getAttributeLookup() {
		return attributeLookup;
	}
	public void setAttrLookupGrpID(Integer attrLookupGrpID) {
		this.attrLookupGrpID = attrLookupGrpID;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public void setAttributeLookup(Set<AttributeLookup> attributeLookup) {
		this.attributeLookup = attributeLookup;
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
	} */

}
