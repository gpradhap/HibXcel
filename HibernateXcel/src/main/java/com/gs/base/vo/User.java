package com.gs.base.vo;

import java.util.Date;

import com.gs.common.util.GenericsUtil;

public class User extends BaseAbstractVo{
	
	//static Logger log = Logger.getLogger(User.class);
	
	//Class className = this.getClass();

	public String userName;
	public String password;
	public String emailId;
	public String roleName;

	/*public String createdBy;
	public Date updateDate;
	public Date expireDate;
	public String updateBy;*/
	
	/*public int hashCode(){
		//return GenericsUtil.genHashCode(className,this);
		return GenericsUtil.genHashCode(this);
	}
	
	public String toString() {
		//StringBuilder strBuilder = GenericsUtil.genToString(className,this);
		StringBuilder strBuilder = GenericsUtil.genToString(this);
		return strBuilder.toString();
	}*/

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
	}*/

	public String getUserName() {
		return userName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

/*	private StringBuilder genToString(Class className) {
		StringBuilder strBuilder = new StringBuilder();
		Method[] methods = className.getDeclaredMethods();
		for (int index = 0; index < methods.length; index++) {
			Method methodLo = methods[index];
			Object getMethodObj = null;
			if (null != methodLo
					&& (methodLo.getName().startsWith("get") || methodLo
							.getName().startsWith("is"))) {
				try {
					getMethodObj = methodLo.invoke(this);
					if (null != getMethodObj) {
						strBuilder.append(methodLo.getName());
						strBuilder.append(":");
						strBuilder.append(getMethodObj.toString());
						strBuilder.append(", ");
					}
				} catch (IllegalArgumentException e) {
					log.error("Exception invoke method "+ methodLo.getName() + " msg:" + e.getMessage());
				} catch (IllegalAccessException e) {
					log.error("Exception invoke method "+ methodLo.getName() + " msg:" + e.getMessage());
				} catch (InvocationTargetException e) {
					log.error("Exception invoke method "+ methodLo.getName() + " msg:" + e.getMessage());
				}
			}
		}
		return strBuilder;
	}*/

}
