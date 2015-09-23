package com.gs.doc.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.gs.base.dao.exception.BaseAbstractDaoException;
import com.gs.base.service.BaseAbstractService;
import com.gs.base.service.UserService;
import com.gs.base.vo.User;
import com.gs.common.mapper.Mapper;
import com.gs.doc.bo.DocumentBo;
import com.gs.doc.bo.mapper.DocumentMapper;
import com.gs.doc.dao.DocumentAttributesDaoImpl;
import com.gs.doc.dao.DocumentDao;
import com.gs.doc.service.exception.DocumentServiceException;
import com.gs.doc.vo.Document;
import com.gs.emp.bo.EmployeeBo;
import com.gs.emp.dao.EmployeeDao;
import com.gs.emp.service.EmployeeService;
import com.gs.emp.vo.Employee;
import com.pg.annotat.utils.HibernateUtil;

public class DocumentService extends BaseAbstractService implements UserService {

	private static Logger log = Logger.getLogger(DocumentService.class);

	//private SessionFactory sessionFactory = null;
	private SessionFactory sessionFactory =  HibernateUtil.getSessionFactory();
	
	public static void main(String...args){
		DocumentService docServ = new DocumentService();
		//docServ.uploadDocument(null);
		/*docServ.createDocument(null);*/
	}
	
	public DocumentService() {
	}

	
	public Document createDocument(Document document) throws DocumentServiceException{
		
		/*DocumentBo documentBo = new DocumentBo();
		documentBo.setCreatedBy("createdBy_Pradhap");
		Date createdDate = null;
		Date currentDate = null;
		try {
			createdDate = new SimpleDateFormat("MM/dd/yyyy").parse("06/16/2015");
			currentDate = new SimpleDateFormat("MM/dd/yyyy").getCalendar().getTime();
		} catch (ParseException e) {
			System.out.println("parsing error"+e.getMessage());		
		}
		documentBo.setCreatedDate(createdDate);
		documentBo.setDocName("Resume");
		documentBo.setUpdatedBy("updateBy_Pradhap");
		documentBo.setUpdatedDate(currentDate);*/
		
		DocumentBo documentBo = Mapper.documentMapper.map(document);
		
		if(null == documentBo){
			log.error("documentBo is null Mapper.documentMapper returns null documentBo");
			return null;
		}
		
		if(null == documentBo.getEmployee() || null == documentBo.getEmployee().getEmployeeId()){
			throw new DocumentServiceException("To add a Document need employeeId");
		}
		
		EmployeeBo employeeBo = new EmployeeDao(sessionFactory).retrieveOne(documentBo.getEmployee());
		
		if(null == employeeBo || null == documentBo.getEmployee()){
			throw new DocumentServiceException("No employee record found for employeeId"+documentBo.getEmployee().getEmployeeId());
		}
		
		List<DocumentBo> documents = new ArrayList<DocumentBo>();
		documents.add(documentBo);
		
		documentBo.getEmployee().setDocuments(documents);
		
		documentBo.setEmployee(employeeBo);
		
		try{
			new DocumentDao(sessionFactory).create(documentBo);
		}catch(BaseAbstractDaoException e){
			e.printStackTrace();
		}
		
		Mapper.documentMapper.map(documentBo, document);
		
		return document;
	}
	
	@Override
	public Document getDocument(Document document) {
		
		if(null == document){return null;}
		
		DocumentBo documentBo = new DocumentBo();
		
		//Mapper.documentMapper.map(document, documentBo);
		documentBo = Mapper.documentMapper.map(document);
		
		if(null == documentBo){
			log.error("documentBo is null Mapper.documentMapper returns null documentBo");
			return null;
		}
		
		/*if(Mapper.documentMapper.isMapSuccess()){
			documentBo = (DocumentBo)Mapper.documentMapper.getMappedObject();
		}*/

		documentBo = new DocumentDao(sessionFactory).retrieveOne(documentBo);
		
		//Mapper.documentMapper.map(documentBo, document);
		document = Mapper.documentMapper.map(documentBo);
		
		return document;
	}

	@Override
	public Boolean deleteDocument(Document document) {
		return null;
	}

	
	@Override
	public Boolean updateDocument(Document document) {
		return null;
	}
	
	
	@Override
	public Boolean uploadDocument(Document document) {

		document = new Document();
		document.setDocName("SSN");
		document.setCreateBy("Pradhap");
		document.setCreateDate(Calendar.getInstance().getTime());

		Employee employee = new Employee();
		Date dob = null;
		try {
			dob = new SimpleDateFormat("dd/MM/yyyy").parse("06/26/1981");
		} catch (ParseException e) {
		}
		employee.setDateOfBirth(dob);
		employee.setFirstName("Pradhap");
		employee.setLastName("Ganesan");
		
		document.setEmployee(employee);
		
		DocumentBo documentBo = new DocumentBo();
		
		Mapper.documentMapper.map(document, documentBo);
		
		documentBo.toString();
		
		//new DocumentDaoImpl(sessionFactory).create(documentAttributesBo);
		return true;
	}
	

	public EmployeeBo addEmployee(EmployeeBo employeeBo) {

		if (null == sessionFactory || sessionFactory.isClosed()) {
			System.out.println("sessionFactory is unavailable");
			return null;
		}
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		EmployeeBo returnEmpBo = null;
		try {
			Object savedObj = session.save(employeeBo);
			returnEmpBo = (savedObj instanceof EmployeeBo) ? (EmployeeBo) savedObj
					: null;

			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return returnEmpBo;
	}
	
	public EmployeeBo saveOrUpdateEmployee(EmployeeBo employeeBo,List<DocumentBo> documentBoLst) {
		if (null == sessionFactory || sessionFactory.isClosed()) {
			System.out.println("sessionFactory is unavailable");
			return null;
		}
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		employeeBo.setDocuments(documentBoLst);
		session.saveOrUpdate(employeeBo);

		/*documentBo.setEmployeeBo(employeeBo);
		session.saveOrUpdate(documentBo);*/

		session.getTransaction().commit();
		session.close();

		return null;
		
	}
	@Override
	public User createUser(User user) {
		return null;
	}

	@Override
	public User retrieveUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean updateUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Boolean deleteUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}	
}
