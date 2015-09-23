package com.gs.common.service;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.gs.common.bo.AttrLookupGroupBo;
import com.gs.common.bo.AttributeLookupBo;
import com.gs.common.service.exception.AttributeServiceException;
import com.gs.common.vo.AttrLookupGroup;
import com.gs.common.vo.AttributeLookup;

public class AttributeLookupServiceTester {

	//@Test
	public void retrieveAttributes(){
		retrieveAttributes_Impl();
	}

	@Test
	public void retrieveAttrWithEmps(){
		retrieveAttrWithEmps_Impl();
	}
	
	//@Test
	public void retrieveAttributesAll(){
		retrieveAttributesAll_Impl();
	}
	
	//@Test
	public void createAttributeLookup(){
		createAttributeLookup_Impl();
	}

	//@Test
	public void updateAttributeLookup(){
		updateAttributeLookup_Impl();
	}
	
	//@Test
	public void deleteAttributeLookup(){
		deleteAttributeLookup_Impl();
	}
	
	//@Test
	public void deleteAttributeLookupAll(){
		deleteAttributeLookupAll_Impl();
	}

	//@Test
	public void retrieveDeletedAttributeLookupAll(){
		retrieveDeletedAttributeLookupAll_Impl();
	}

	private void retrieveDeletedAttributeLookupAll_Impl(){
		
		AttributeLookup attributeLookup = new AttributeLookup(); 
		
		Set<AttributeLookup> attributes = null;
		try{
		
			attributes = new AttributeLookupService().retrieveDeletedAttributes(attributeLookup);

		}catch(AttributeServiceException e){
			e.printStackTrace();
		}
		
		for(AttributeLookup attributeLookupLo:attributes){
			Assert.assertNotNull(attributeLookupLo);
			
			System.out.println("attributeLookup "+attributeLookupLo.toString());
			
			//System.out.println("\tAttrLookupGroup "+attributeLookupLo.getAttrLookupGroup().toString());
		}
	}
	
	private void deleteAttributeLookupAll_Impl(){

		AttributeLookup attributeLookup = new AttributeLookup();
		AttrLookupGroup attrLookupGroup = new AttrLookupGroup("EMP_DOC_TEMPII");

		/*attributeLookup.setAttribute("EMP_DOC_TEMP_Dup_up");
		attributeLookup.setUpdateBy("EMP_DOC_TEMP_Dup_up3");
		attributeLookup.setUserName("EMP_DOC_TEMP_Dup_up3");*/

		/*Set<AttributeLookup> attributeLookupList = new HashSet<AttributeLookup>();
		attributeLookupList.add(attributeLookup);

		attrLookupGroup.setAttributeLookup(attributeLookupList);
		attributeLookup.setAttrLookupGroup(attrLookupGroup);*/

		Boolean deleteAttributes = false;
		
		try{
			deleteAttributes = new AttributeLookupService().deleteAttributeLookupAll(attributeLookup);
		}catch(AttributeServiceException e){
			e.printStackTrace();
		}

		Assert.assertTrue(deleteAttributes);

		System.out.println("attributeLookup " + deleteAttributes);

	}
	
	private void deleteAttributeLookup_Impl(){

		AttributeLookup attributeLookup = new AttributeLookup();
		//AttrLookupGroup attrLookupGroup = new AttrLookupGroup("EMP_DOC_TEMP_Dup");

		attributeLookup.setAttribute("EMP_DOC_TEMP_Dup_up");
		attributeLookup.setUpdateBy("EMP_DOC_TEMP_Dup_up3");

		Set<AttributeLookup> attributeLookupList = new HashSet<AttributeLookup>();
		attributeLookupList.add(attributeLookup);

		/*attrLookupGroup.setAttributeLookup(attributeLookupList);

		attributeLookup.setAttrLookupGroup(attrLookupGroup);*/

		Boolean deleteAttributes = false;
		
		try{
			deleteAttributes = new AttributeLookupService().deleteAttributeLookup(attributeLookup);
		}catch(AttributeServiceException e){
			e.printStackTrace();
		}

		Assert.assertTrue(deleteAttributes);

		System.out.println("attributeLookup " + deleteAttributes);

	}
	
	private void updateAttributeLookup_Impl(){

		AttributeLookup attributeLookup = new AttributeLookup();
		//AttrLookupGroup attrLookupGroup = new AttrLookupGroup("EMP_DOC_TEMP_Dup");

		attributeLookup.setAttribute("EMP_DOC_TEMP_Dup_up");
		attributeLookup.setUpdateBy("EMP_DOC_TEMP_Dup_up3");

		Set<AttributeLookup> attributeLookupList = new HashSet<AttributeLookup>();
		attributeLookupList.add(attributeLookup);

		/*attrLookupGroup.setAttributeLookup(attributeLookupList);

		attributeLookup.setAttrLookupGroup(attrLookupGroup);*/

		AttributeLookup attributes = null;
		
		try{
			attributes = new AttributeLookupService().updateAttributeLookup(attributeLookup);
		}catch(AttributeServiceException e){
			e.printStackTrace();
		}

		Assert.assertNotNull(attributes);

		System.out.println("attributeLookup " + attributes.toString());

	}
	
	private void createAttributeLookup_Impl() {

		AttributeLookup attributeLookup = new AttributeLookup();
		AttrLookupGroup attrLookupGroup = new AttrLookupGroup("EMP_DOC_TEMP_Dup");

		attributeLookup.setAttribute("EMP_DOC_TEMP_Dup");
		attributeLookup.setUpdateBy("EMP_DOC_TEMP_Dup");
		
		Set<AttributeLookup> attributeLookupList = new HashSet<AttributeLookup>();
		attributeLookupList.add(attributeLookup);

		attrLookupGroup.setAttributeLookup(attributeLookupList);

		attributeLookup.setAttrLookupGroup(attrLookupGroup);

		AttributeLookup attributes = null;
		
		try{
			attributes = new AttributeLookupService().createAttributeLookup(attributeLookup);
		}catch(AttributeServiceException e){
			e.printStackTrace();
		}

		Assert.assertNotNull(attributes);

		System.out.println("attributeLookup " + attributes.toString());

	}

	private void retrieveAttributesAll_Impl() {
		
		AttributeLookup attributeLookup = new AttributeLookup(); 

		AttrLookupGroup attrLookupGroup = new AttrLookupGroup();
		attrLookupGroup.setAttrLookupGrpID(2);
		attributeLookup.setAttrLookupGroup(attrLookupGroup);
		
		Set<AttributeLookup> attributes = new AttributeLookupService().retrieveAttributes(attributeLookup);
		
		for(AttributeLookup attributeLookupLo:attributes){
			Assert.assertNotNull(attributeLookupLo);
			
			System.out.println("attributeLookup "+attributeLookupLo.toString());
			
			//System.out.println("\tAttrLookupGroup "+attributeLookupLo.getAttrLookupGroup().toString());
		}
	}
	
	private void retrieveAttrWithEmps_Impl() {
		AttrLookupGroup attrLookupGroup = new AttrLookupGroup(2); 
		Set<AttributeLookup> attributes = null;
		try{
			
			attributes = new AttributeLookupService().retrieveAttrWithEmps(attrLookupGroup);
			
		}catch(AttributeServiceException e){
			e.printStackTrace();
		}
		
		for(AttributeLookup attributeLookup:attributes){
			Assert.assertNotNull(attributeLookup);
			
			System.out.println("attributeLookup "+attributeLookup.toString());
			
			System.out.println("\tAttrLookupGroup "+attributeLookup.getAttrLookupGroup().toString());
		}
	}
	
	private void retrieveAttributes_Impl() {
		AttrLookupGroup attrLookupGroup = new AttrLookupGroup(2); 
		
		Set<AttributeLookup> attributes = new AttributeLookupService().retrieveAttributes(attrLookupGroup);
		
		for(AttributeLookup attributeLookup:attributes){
			Assert.assertNotNull(attributeLookup);
			
			System.out.println("attributeLookup "+attributeLookup.toString());
			
			System.out.println("\tAttrLookupGroup "+attributeLookup.getAttrLookupGroup().toString());
		}
	}
	
}
