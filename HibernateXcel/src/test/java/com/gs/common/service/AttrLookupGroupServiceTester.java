package com.gs.common.service;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;

import com.gs.base.service.exception.BaseAbstractException;
import com.gs.common.vo.AttrLookupGroup;
import com.gs.common.vo.AttributeLookup;

public class AttrLookupGroupServiceTester {

	//@Test
	public void createAttrLookupGrp_EmpHomeAddr(){
		createAttrLookupGrp_EmpHomeAddr_Impl();
	}

	@Test
	public void createAttrLookupGrp_EmpPersDtls(){
		createAttrLookupGrp_EmpPersDtls_Impl();
	}

	//@Test
	public void createAttrLookupGrp(){
		
		createAttrLookupGrp_Impl();
	}

	//@Test
	public void retrieveAll(){
		retrieveAll_Impl();
	}

	//@Test
	public void updateAttrLookupGrp_I(){
		updateAttrLookupGrp_I_impl();
	}

	//@Test
	public void updateAttrLookupGrp_II(){
		
		updateAttrLookupGrp_II_Impl();
	}

	//@Test
	public void deleteAttrLookupGrp(){
		
		deleteAttrLookupGrp_Impl();
	}

	private void createAttrLookupGrp_EmpPersDtls_Impl(){
		AttrLookupGroup attrLookupGroup = new AttrLookupGroup("EMP_PERS_DETAILS");
		attrLookupGroup.setUpdateBy("Admin");
		
		Set<AttributeLookup> attributeLookupSet = new LinkedHashSet<AttributeLookup>();
		
		
		AttributeLookup attrLk_1 = new AttributeLookup();
		attrLk_1.setAttribute("FIRST_NAME");
		attrLk_1.setAttrDesc("First Name");
		attrLk_1.setDisplayOrder(1);
		attrLk_1.setAttrLookupGroup(attrLookupGroup);
		attributeLookupSet.add(attrLk_1);

		AttributeLookup attrLk_2 = new AttributeLookup();
		attrLk_2.setAttribute("MID_NAME");
		attrLk_2.setAttrDesc("Middle Name");
		attrLk_2.setDisplayOrder(2);
		attrLk_2.setAttrLookupGroup(attrLookupGroup);
		attributeLookupSet.add(attrLk_2);

		AttributeLookup attrLk_3 = new AttributeLookup();
		attrLk_3.setAttribute("LAST_NAME");
		attrLk_3.setAttrDesc("Last Name");
		attrLk_3.setDisplayOrder(3);
		attrLk_3.setAttrLookupGroup(attrLookupGroup);
		attributeLookupSet.add(attrLk_3);

		AttributeLookup attrLk_4 = new AttributeLookup();
		attrLk_4.setAttribute("SUFFIX");
		attrLk_4.setAttrDesc("Suffix");
		attrLk_4.setDisplayOrder(4);
		attrLk_4.setAttrLookupGroup(attrLookupGroup);
		attributeLookupSet.add(attrLk_4);

		AttributeLookup attrLk_5 = new AttributeLookup();
		attrLk_5.setAttribute("GENDER");
		attrLk_5.setAttrDesc("gender");
		attrLk_5.setDisplayOrder(5);
		attrLk_5.setAttrLookupGroup(attrLookupGroup);
		attributeLookupSet.add(attrLk_5);

		AttributeLookup attrLk_6 = new AttributeLookup();
		attrLk_6.setAttribute("DOB");
		attrLk_6.setAttrDesc("Date Of Birth");
		attrLk_6.setDisplayOrder(6);
		attrLk_6.setAttrLookupGroup(attrLookupGroup);
		attributeLookupSet.add(attrLk_6);

		AttributeLookup attrLk_7 = new AttributeLookup();
		attrLk_7.setAttribute("COB");
		attrLk_7.setAttrDesc("City Of Birth");
		attrLk_7.setDisplayOrder(7);
		attrLk_7.setAttrLookupGroup(attrLookupGroup);
		attributeLookupSet.add(attrLk_7);

		AttributeLookup attrLk_8 = new AttributeLookup();
		attrLk_8.setAttribute("SOB");
		attrLk_8.setAttrDesc("State Of Birth");
		attrLk_8.setDisplayOrder(8);
		attrLk_8.setAttrLookupGroup(attrLookupGroup);
		attributeLookupSet.add(attrLk_8);

		AttributeLookup attrLk_9 = new AttributeLookup();
		attrLk_9.setAttribute("COOB");
		attrLk_9.setAttrDesc("Country Of Birth");
		attrLk_9.setDisplayOrder(8);
		attrLk_9.setAttrLookupGroup(attrLookupGroup);
		attributeLookupSet.add(attrLk_9);

		attrLookupGroup.setAttributeLookup(attributeLookupSet);

		System.out.println("Before Create "+attrLookupGroup.toString());
		
		attrLookupGroup = new AttrLookupGroupService().createAttrLookupGrp(attrLookupGroup);
		
		System.out.println("After Create "+attrLookupGroup.toString());
	}
	
	private void createAttrLookupGrp_EmpHomeAddr_Impl(){
		AttrLookupGroup attrLookupGroup = new AttrLookupGroup("EMP_HOME_ADDRESS");
		attrLookupGroup.setUpdateBy("Admin");
		
		Set<AttributeLookup> attributeLookupSet = new LinkedHashSet<AttributeLookup>();
		
		
		AttributeLookup attrLk_Address1 = new AttributeLookup();
		attrLk_Address1.setAttribute("ADDRESS1");
		attrLk_Address1.setAttrLookupGroup(attrLookupGroup);
		attributeLookupSet.add(attrLk_Address1);

		AttributeLookup attrLk_Address2 = new AttributeLookup();
		attrLk_Address2.setAttribute("ADDRESS2");
		attrLk_Address2.setAttrLookupGroup(attrLookupGroup);
		attributeLookupSet.add(attrLk_Address2);

		AttributeLookup attrLk_city = new AttributeLookup();
		attrLk_city.setAttribute("CITY");
		attrLk_city.setAttrLookupGroup(attrLookupGroup);
		attributeLookupSet.add(attrLk_city);

		AttributeLookup attrLk_state = new AttributeLookup();
		attrLk_state.setAttribute("STATE");
		attrLk_state.setAttrLookupGroup(attrLookupGroup);
		attributeLookupSet.add(attrLk_state);

		AttributeLookup attrLk_zip = new AttributeLookup();
		attrLk_zip.setAttribute("ZIP");
		attrLk_zip.setAttrLookupGroup(attrLookupGroup);
		attributeLookupSet.add(attrLk_zip);
		
		attrLookupGroup.setAttributeLookup(attributeLookupSet);

		System.out.println("Before Create "+attrLookupGroup.toString());
		
		attrLookupGroup = new AttrLookupGroupService().createAttrLookupGrp(attrLookupGroup);
		
		System.out.println("After Create "+attrLookupGroup.toString());
	}
	
	private void deleteAttrLookupGrp_Impl() {
		AttrLookupGroup attrLookupGroup = new AttrLookupGroup("TEMP_GRP_1");
		
		AttributeLookup attributeLookup = new AttributeLookup();
		attributeLookup.setAttribute("TEMP_ATTR_1");
		attributeLookup.setAttrLookupGroup(attrLookupGroup);
		
		Set<AttributeLookup> attributeLookupSet = new HashSet<AttributeLookup>();
		attributeLookupSet.add(attributeLookup);
		
		attrLookupGroup.setUpdateBy("GP");
		attrLookupGroup.setAttributeLookup(attributeLookupSet);

		System.out.println("Before delete "+attrLookupGroup.toString());
		
		boolean isRecordDelete = false;
		try{
			isRecordDelete = new AttrLookupGroupService().deleteAttrLookupGrp(attrLookupGroup);
		}catch(BaseAbstractException e){
			e.printStackTrace();
		}
		
		System.out.println(" delete? "+isRecordDelete);
	}
	
	private void retrieveAll_Impl() {
		Set<AttrLookupGroup> attrLookupGroupSet = new AttrLookupGroupService().retrieveAll();
		
		Assert.assertNotNull(attrLookupGroupSet);
		
		for(AttrLookupGroup attrLookupGroup:attrLookupGroupSet){
			Assert.assertNotNull(attrLookupGroup);
			System.out.println("GP: attrLookupGroup: "+attrLookupGroup.toString());
			if(null != attrLookupGroup.getAttributeLookup()){
				System.out.println("\t\tGP: AttributeLookup :" + attrLookupGroup.getAttributeLookup().toString());
			}
		}
	}


	private void updateAttrLookupGrp_I_impl() {
		AttrLookupGroup attrLookupGroup = new AttrLookupGroup("EMP_PERS_DTLS");
		attrLookupGroup.setUpdateBy("PG");
		
		System.out.println("Before update "+attrLookupGroup.toString());
		

		try{
			attrLookupGroup = new AttrLookupGroupService().updateAttrLookupGrp(attrLookupGroup);
		}catch(BaseAbstractException e){
			e.printStackTrace();
		}
		
		System.out.println(" update? "+attrLookupGroup);
	}

	private void updateAttrLookupGrp_II_Impl() {
		AttrLookupGroup attrLookupGroup = new AttrLookupGroup("TEMP_GRP_2");
		
		AttributeLookup attributeLookup = new AttributeLookup();
		attributeLookup.setAttribute("TEMP_ATTR_2");
		attributeLookup.setUpdateBy("GP");
		attributeLookup.setAttrLookupGroup(attrLookupGroup);
		
		Set<AttributeLookup> attributeLookupSet = new HashSet<AttributeLookup>();
		attributeLookupSet.add(attributeLookup);
		attrLookupGroup.setAttributeLookup(attributeLookupSet);
		attributeLookup.setUpdateBy("GP");


		System.out.println("Before update "+attrLookupGroup.toString());

		try{
			attrLookupGroup = new AttrLookupGroupService().updateAttrLookupGrp(attrLookupGroup);
		}catch(BaseAbstractException e){
			e.printStackTrace();
		}
		
		System.out.println(" updated ? "+attrLookupGroup);
	}	

	private void createAttrLookupGrp_Impl() {
		AttrLookupGroup attrLookupGroup = new AttrLookupGroup("TEMP_GRP_1");
		attrLookupGroup.setUpdateBy("GP");
		
		AttributeLookup attributeLookup = new AttributeLookup();
		attributeLookup.setAttribute("TEMP_ATTR_1");
		attributeLookup.setAttrLookupGroup(attrLookupGroup);
		
		Set<AttributeLookup> attributeLookupSet = new HashSet<AttributeLookup>();
		attributeLookupSet.add(attributeLookup);
		
		attrLookupGroup.setAttributeLookup(attributeLookupSet);

		System.out.println("Before Create "+attrLookupGroup.toString());
		
		attrLookupGroup = new AttrLookupGroupService().createAttrLookupGrp(attrLookupGroup);
		
		System.out.println("After Create "+attrLookupGroup.toString());
	}
}
