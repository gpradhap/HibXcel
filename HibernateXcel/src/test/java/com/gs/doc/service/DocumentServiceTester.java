package com.gs.doc.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.SimpleFormatter;

import org.junit.Assert;
import org.junit.Test;

import com.gs.doc.service.DocumentService;
import com.gs.doc.service.exception.DocumentServiceException;
import com.gs.doc.vo.Document;
import com.gs.emp.vo.Employee;

public class DocumentServiceTester {

	static DocumentService docService = new DocumentService();
	
	@Test
	public void addDocument() {
		//addDocumentMthd();
	}
	
	@Test
	public void getDocument() {
		getDocumentMthd();
	}

	@Test
	public void getUser() {
		
	}

	private void getDocumentMthd() {
		Document document = new Document();
		document.setDocumentId(2L);

		document = docService.getDocument(document);

		System.out.println(document.toString());
	}
	
	private void addDocumentMthd() {

		Document document = new Document();
		document.setDocName("Passport");
		document.setCreateBy("createdBy_pg");
		document.setCreateDate(Calendar.getInstance().getTime());
		document.setUpdateBy("updatedBy_pg");
		document.setUpdateDate(Calendar.getInstance().getTime());

		Employee employee = new Employee();
		employee.setEmployeeId(2);
		
		List<Document> documentList = new ArrayList<Document>();
		documentList.add(document);
		employee.setDocuments(documentList);
		
		document.setEmployee(employee);

		Document newDocument=null;
		try{

			newDocument = docService.createDocument(document);

		}catch(DocumentServiceException e){
			e.getErrorCode(); }

		Assert.assertNotNull(newDocument);
	}

}
