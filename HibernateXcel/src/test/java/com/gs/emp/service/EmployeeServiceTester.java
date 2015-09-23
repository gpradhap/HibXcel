package com.gs.emp.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.SimpleFormatter;

import org.junit.Assert;
import org.junit.Test;

import com.gs.common.service.AttrLookupGroupService;
import com.gs.common.vo.AttrLookupGroup;
import com.gs.common.vo.AttributeLookup;
import com.gs.doc.vo.Document;
import com.gs.emp.bo.EmployeeAttributesBo;
import com.gs.emp.vo.Employee;
import com.gs.emp.vo.EmployeeAttributes;

public class EmployeeServiceTester {

	static EmployeeService empService = new EmployeeService();
	
	//@Test
	public void addEmployee() {
		addEmployeeMthd();
	}
	
	@Test
	public void addEmployeeWithAttr() {
		addEmployeeWithAttr_Impl();
	}
	
	//@Test
	public void addEmployeeAttributes() {
		addEmployeeAttributes_Impl();
	}
	
	//@Test
	public void getEmployee() {
		getEmployeeMthd();
	}

	@Test
	public void getEmployeeWithAttr() {
		getEmployeeWithAttr_Impl();
	}
	//@Test
	public void retrieveEmployeeAll() {
		retrieveEmployeeAll_Impl();
	}
	
	//@Test
	public void getUser() {
		
	}

	private void addEmployeeAttributes_Impl(){
		empService.addEmployeeAttributes();
	}
	
	private void addEmployeeWithAttr_Impl() {

		Employee employee = new Employee(6);
		employee.setUpdateBy("GP");

		System.out.println(" newEmployee ?? "+employee.toString());
		
		AttrLookupGroup attrLookupGroup = new AttrLookupGroup(19);

		if(null == attrLookupGroup){
			System.out.println(" no record returns " + attrLookupGroup);
			return;
		}
		
		Set<EmployeeAttributes> empAttributeSet = new LinkedHashSet<EmployeeAttributes>();

		EmployeeAttributes employeeAttr1 = new EmployeeAttributes();
			employeeAttr1.setAttributeName("FIRST_NAME");
			employeeAttr1.setAttributeValue("Pradhap");
			empAttributeSet.add(employeeAttr1);

		EmployeeAttributes employeeAttr2 = new EmployeeAttributes();
			employeeAttr2.setAttributeName("MID_NAME");
			employeeAttr2.setAttributeValue("");
			empAttributeSet.add(employeeAttr2);
	
		EmployeeAttributes employeeAttr3 = new EmployeeAttributes();
			employeeAttr3.setAttributeName("LAST_NAME");
			employeeAttr3.setAttributeValue("Ganesan");
			empAttributeSet.add(employeeAttr3);

		EmployeeAttributes employeeAttr4 = new EmployeeAttributes();
			employeeAttr4.setAttributeName("SUFFIX");
			employeeAttr4.setAttributeValue("");
			empAttributeSet.add(employeeAttr4);

			EmployeeAttributes employeeAttr5 = new EmployeeAttributes();
			employeeAttr5.setAttributeName("GENDER");
			employeeAttr5.setAttributeValue("Male");
			empAttributeSet.add(employeeAttr5);

			EmployeeAttributes employeeAttr6 = new EmployeeAttributes();
			employeeAttr6.setAttributeName("DOB");
			employeeAttr6.setAttributeValue("06/26/1981");
			empAttributeSet.add(employeeAttr6);

			EmployeeAttributes employeeAttr7 = new EmployeeAttributes();
			employeeAttr7.setAttributeName("COB");
			employeeAttr7.setAttributeValue("Gudiyatham");
			empAttributeSet.add(employeeAttr7);

			EmployeeAttributes employeeAttr8 = new EmployeeAttributes();
			employeeAttr8.setAttributeName("SOB");
			employeeAttr8.setAttributeValue("Tamilnadu");
			empAttributeSet.add(employeeAttr8);

			EmployeeAttributes employeeAttr9 = new EmployeeAttributes();
			employeeAttr9.setAttributeName("COOB");
			employeeAttr9.setAttributeValue("India");
			empAttributeSet.add(employeeAttr9);

		employee.setEmpAttributeSet(empAttributeSet);
		System.out.println(" employee ? "+employee.toString());
		
		Employee newEmployee = empService.addEmployeeWithAttr(employee,attrLookupGroup);
		
		System.out.println(" newEmployee ? "+newEmployee.toString());
		
		Assert.assertNotNull(newEmployee);
	}
	
	private void retrieveEmployeeAll_Impl(){
		Employee employee = new Employee();

		Set<Employee> empSet = empService.retrieveEmployeeAll(employee);

		System.out.println(" RESULT "+empSet.toString());
	}
	
	private void getEmployeeWithAttr_Impl(){
		Employee employee = new Employee();
		employee.setEmployeeId(6);

		empService.retrieveEmployeeWithAttr(employee);

		System.out.println(" RESULT "+employee.toString());
	}
	
	private void getEmployeeMthd() {
		Employee employee = new Employee();
		employee.setEmployeeId(2);

		empService.retrieveEmployee(employee);

		System.out.println(" RESULT "+employee.toString());
	}
	
	private void addEmployeeMthd() {

		Employee employee = new Employee();
		employee.setFirstName("Pradhap5");
		employee.setLastName("Ganesan5");
		employee.setOnlineUserId("pradhap_5");
		//employee.setEmployeeId(2L);
		try{
			employee.setDateOfBirth(new SimpleDateFormat("mm/dd/yyyy").parse("06/26/1985"));
		}catch(ParseException e){
			System.out.println("DOB not set"+e.getMessage());
		}

		Document document = new Document();
		document.setDocName("Passport");
		document.setCreateBy("createdBy_pg");
		document.setCreateDate(Calendar.getInstance().getTime());
		document.setUpdateBy("updatedBy_pg");
		document.setUpdateDate(Calendar.getInstance().getTime());
		document.setEmployee(employee);

		List<Document> documentList = new ArrayList<Document>();
		documentList.add(document);

		employee.setDocuments(documentList);

		Employee newEmployee = empService.addEmployee(employee);

		Assert.assertNotNull(newEmployee);
	}

}
