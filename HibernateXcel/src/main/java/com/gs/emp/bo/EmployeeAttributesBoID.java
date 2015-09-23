package com.gs.emp.bo;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import com.gs.common.bo.AttributeLookupBo;

@Embeddable
public class EmployeeAttributesBoID implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EmployeeBo employee;
	private AttributeLookupBo attributeLookup;
	
	@ManyToOne(cascade = CascadeType.ALL)
	//@Cascade(value={CascadeType.ALL})
	public EmployeeBo getEmployee() {
		return employee;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	//@Cascade(value={CascadeType.ALL})
	public AttributeLookupBo getAttributeLookup() {
		return attributeLookup;
	}

	public void setAttributeLookup(AttributeLookupBo attributeLookup) {
		this.attributeLookup = attributeLookup;
	}

	public void setEmployee(EmployeeBo employee) {
		this.employee = employee;
	}

	/*private AttrLookupGroupBo attrLookupGroupBo;
	@ManyToOne
	@Cascade(value={CascadeType.ALL})
	public AttrLookupGroupBo getAttrLookupGroupBo() {
		return attrLookupGroupBo;
	}
	
	public void setAttrLookupGroupBo(AttrLookupGroupBo attrLookupGroupBo) {
		this.attrLookupGroupBo = attrLookupGroupBo;
	}*/
	
	/*@Override
	public int hashCode() {
		return super.hashCode();
	}*/
	
	public boolean equals(Object inObj) {
        if (this == inObj) return true;
        if (inObj == null || getClass() != inObj.getClass()) return false;
 
        EmployeeAttributesBoID that = (EmployeeAttributesBoID) inObj;
 
		if(employee != null && that.employee != null ? !(employee.hashCode() == that.employee
				.hashCode()) : true){
			return false;
		}
        if (attributeLookup != null && that.attributeLookup != null ? !(attributeLookup.hashCode() == that.attributeLookup.hashCode()) : true){
            return false;
        }
 
        return true;
    }
	//public boolean equals(Object inObj) {return true;}
}
