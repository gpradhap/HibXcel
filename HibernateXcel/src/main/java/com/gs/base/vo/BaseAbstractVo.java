package com.gs.base.vo;

import java.util.Date;

import com.gs.common.util.GenericsUtil;

public class BaseAbstractVo {
	
	public String createBy;
	public Date createDate;
	public Date updateDate;
	public String updateBy;
	public Date expireDate;
	
	public Date getUpdateDate() {
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
	}

	public int hashCode(){
		//return GenericsUtil.genHashCode(className,this);
		return GenericsUtil.genHashCode(this);
	}
	
	public String toString() {
		//StringBuilder strBuilder = GenericsUtil.genToString(className,this);
		StringBuilder strBuilder = GenericsUtil.genToString(this);
		return strBuilder.toString();
	}

	public String getCreateBy() {
		return createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}
