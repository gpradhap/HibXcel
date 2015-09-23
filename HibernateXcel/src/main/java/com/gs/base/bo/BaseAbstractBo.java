package com.gs.base.bo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.gs.common.util.GenericsUtil;
import com.gs.common.util.JavaUtilDateType;

@MappedSuperclass
@TypeDefs({ @TypeDef(name = "javaUtilDateType", defaultForType = java.util.Date.class, typeClass = JavaUtilDateType.class) })
public class BaseAbstractBo {
	
	//static Logger log = Logger.getLogger(User.class);
	
	//Class className = this.getClass();
	
	public String createBy;
	public Date createDate;

	public Date updateDate;
	public Date expireDate;
	public String updateBy;	
	
	public int hashCode(){
		//return GenericsUtil.genHashCode(className,this);
		return GenericsUtil.genHashCode(this);
	}
	
	public String toString() {
		//StringBuilder strBuilder = GenericsUtil.genToString(className,this);
		StringBuilder strBuilder = GenericsUtil.genToString(this);
		return strBuilder.toString();
	}
	
	@Column(name="update_dt")
	@Type(type="javaUtilDateType")
	public Date getUpdateDate() {
		/*if(null == this.updateDate){
			this.updateDate = Calendar.getInstance().getTime();
		}*/
		return this.updateDate;
	}

	@Column(name="expire_dt")
	@Type(type="javaUtilDateType")
	public Date getExpireDate() {
		/*if(null == this.expireDate){
			try{
				expireDate = new SimpleDateFormat("MM/dd/yyyy").parse(EntityConstants.COLUMN.expiredDate_VALUE.getStrValue());
			}catch(ParseException e){
				log.error("Date Parsing error "+e.getMessage());
			}
		}*/
		return this.expireDate;
	}

	@Column(name="update_by")
	public String getUpdateBy() {
		/*if(null == this.updateBy){
			this.updateBy = "N/A";
		}*/		
		return this.updateBy;
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

	@Transient
	public String getCreateBy() {
		return createBy;
	}

	@Transient
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	/*public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		Class<? extends BaseAbstractBo> className = this.getClass();
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
					System.out.println("Exception invoke method "+ methodLo.getName() + " msg:" + e.getMessage());
				} catch (IllegalAccessException e) {
					System.out.println("Exception invoke method "+ methodLo.getName() + " msg:" + e.getMessage());
				} catch (InvocationTargetException e) {
					System.out.println("Exception invoke method "+ methodLo.getName() + " msg:" + e.getMessage());
				}
			}
		}
		return strBuilder.toString();
	}*/

}
