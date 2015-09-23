package com.gs.emp.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.gs.base.service.BaseAbstractService;
import com.gs.base.service.UserService;
import com.gs.base.vo.User;
import com.gs.common.bo.AttrLookupGroupBo;
import com.gs.common.bo.AttributeLookupBo;
import com.gs.common.dao.AttrLookupGroupDao;
import com.gs.common.mapper.Mapper;
import com.gs.common.vo.AttrLookupGroup;
import com.gs.doc.bo.DocumentBo;
import com.gs.doc.vo.Document;
import com.gs.emp.bo.EmployeeAttributesBo;
import com.gs.emp.bo.EmployeeBo;
import com.gs.emp.dao.EmployeeDao;
import com.gs.emp.vo.Employee;

public class EmployeeService extends BaseAbstractService implements UserService {

	private static Logger log = Logger.getLogger(EmployeeService.class);
	
	//private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	public Employee retrieveEmployee(Integer employeeId) {
		
		if(null == employeeId){return null;}
		
		if(log.isTraceEnabled()){
			log.trace("mapping objects about to begin employee -> employeeBo");
		}

		EmployeeBo employeeBo = new EmployeeBo(employeeId);
		
		if(employeeBo == null){
			log.error("mapping objects was NOT successfull employee -> employeeBo ?:"+employeeBo);
			return null;
		}
		
		employeeBo = new EmployeeDao(getSessionFactory()).retrieveOne(employeeBo);

		Employee employee = Mapper.employeeMapper.map(employeeBo);

		return employee;
	}

	
	public Employee retrieveEmployee(Employee employee) {
		
		if(null == employee){return null;}
		
		if(log.isTraceEnabled()){
			log.trace("mapping objects about to begin employee -> employeeBo");
		}
		
		EmployeeBo employeeBo = Mapper.employeeMapper.map(employee);
		
		if(employeeBo == null){
			log.error("mapping objects was NOT successfull employee -> employeeBo ?:"+employeeBo);
			return null;
		}
		
		employeeBo = new EmployeeDao(getSessionFactory()).retrieveOne(employeeBo);

		employee = Mapper.employeeMapper.map(employeeBo);

		return employee;
	}
	
	public Employee retrieveEmployeeWithAttr(Employee employee) {
		
		if(null == employee){return null;}
		
		if(log.isTraceEnabled()){
			log.trace("mapping objects about to begin employee -> employeeBo");
		}
		
		EmployeeBo employeeBo = Mapper.employeeMapper.map(employee);
		
		if(employeeBo == null){
			log.error("mapping objects was NOT successfull employee -> employeeBo ?:"+employeeBo);
			return null;
		}
		
		employeeBo = new EmployeeDao(getSessionFactory()).retrieveEmployeeWithAttr(employeeBo);

		employee = Mapper.employeeMapper.map(employeeBo);

		return employee;
	}

	public Set<Employee> retrieveEmployeeAll(Employee employee) {

		if(null == employee){
			employee = new Employee();
		}

		if(log.isTraceEnabled()){
			log.trace("mapping objects about to begin employee -> employeeBo");
		}

		EmployeeBo employeeBo = Mapper.employeeMapper.map(employee);

		if(employeeBo == null){
			log.error("mapping NOT successfull employee -> employeeBo ?:" + employeeBo);
			return null;
		}

		Set<EmployeeBo> employeeBoSet = new EmployeeDao(getSessionFactory()).retrieveSet(employeeBo);

		if(null == employeeBoSet || employeeBoSet.size() == 0){
			log.error("No records are return for " + employee.toString());
			return null;
		}

		Set<Employee> employeeSet = new HashSet<Employee>();

		for(EmployeeBo employeeBoLo : employeeBoSet){
			if(null == employeeBoLo){
				continue;
			}

			Employee empLo = Mapper.employeeMapper.map(employeeBoLo);
			if(null != empLo){
				employeeSet.add(empLo);
			}
		}
		return employeeSet;
	}
	
	public Employee addEmployee(Employee employee) {

		if (null == employee) {
			return null;
		}
		
		EmployeeBo employeeBo = Mapper.employeeMapper.map(employee);
		
		try {
			employeeBo = new EmployeeDao(getSessionFactory()).create(employeeBo);

		} catch (Exception e) {
			log.error("EXCEPTION: creating employee failed "+e.getMessage());}

		employee = Mapper.employeeMapper.map(employeeBo);
		
		return employee;
	}

	public Employee addEmployeeWithAttr(Employee employee,AttrLookupGroup attrLookupGrp) {

		if (null == employee || null == attrLookupGrp) {
			return null;
		}
		
		//Set<EmployeeAttributes> empAttributeSet = employee.getEmpAttributeSet();
		
		EmployeeBo employeeBo = Mapper.employeeMapper.map(employee);
		AttrLookupGroupBo attrLookupGroupBo = Mapper.attributeMapper.map(attrLookupGrp);
		//employeeBo.getEmpAttributeSet();
		if(null != employeeBo){
			System.out.println(" employeeBo ? "+employeeBo.toString());
		}
		
		try {
			employeeBo = new EmployeeDao(getSessionFactory()).addEmployeeAttributes(employeeBo, attrLookupGroupBo);

		} catch (Exception e) {
			log.error("EXCEPTION: creating employee failed "+e.getMessage());}

		employee = Mapper.employeeMapper.map(employeeBo);
		
		return employee;
	}
	
	public void addEmployeeAttributes() {
		
		EmployeeBo employeeBo;
		try{
			new EmployeeDao(getSessionFactory()).addEmployeeAttributes();
		}catch(Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*

		EmployeeBo employeeBo = new EmployeeDao().retrieveOne(new EmployeeBo(6));

		System.out.println(" employeeBo ?? " + employeeBo.toString());

		AttrLookupGroupBo attrLookupGroupBo = new AttrLookupGroupDao(getSessionFactory())
				.retrieveOne(new AttrLookupGroupBo(18));

		if(null == attrLookupGroupBo){
			System.out.println(" no record returns " + attrLookupGroupBo);
			return;
		}

		//Set<EmployeeAttributesBo> empAttributeSet = new LinkedHashSet<EmployeeAttributesBo>();
		Set<EmployeeAttributesBo> empAttributeSet = employeeBo.getEmpAttributeSet();

		for(AttributeLookupBo attributeLookupLo : attrLookupGroupBo.getAttributeLookup()){
			if(null != attributeLookupLo){
				EmployeeAttributesBo employeeAttributes = new EmployeeAttributesBo();
				employeeAttributes.setAttributeLookup(attributeLookupLo);

				if("ADDRESS1".equals(attributeLookupLo.getAttribute())){
					employeeAttributes.setAttributeValue("9700 NW 112th Ave");
				}else if("ADDRESS2".equals(attributeLookupLo.getAttribute())){
					employeeAttributes.setAttributeValue("");
				}else if("CITY".equals(attributeLookupLo.getAttribute())){
					employeeAttributes.setAttributeValue("Miami");
				}else if("STATE".equals(attributeLookupLo.getAttribute())){
					employeeAttributes.setAttributeValue("Florida");
				}else if("ZIP".equals(attributeLookupLo.getAttribute())){
					employeeAttributes.setAttributeValue("33178");
				}
				empAttributeSet.add(employeeAttributes);
			}
		}

		employeeBo.setEmpAttributeSet(empAttributeSet);
		System.out.println(" employee ? " + employeeBo.toString());

		try{
			employeeBo = new EmployeeDao(getSessionFactory()).create(employeeBo);

		}catch(Exception e){
			log.error("EXCEPTION: creating employee failed " + e.getMessage());
		}

		System.out.println(" new employeeBo ? " + employeeBo.toString());
		return;
	*/}
	
	public EmployeeBo saveOrUpdateEmployee(EmployeeBo employeeBo,
			List<DocumentBo> documentBoLst) {
		if (null == getSessionFactory() || getSessionFactory().isClosed()) {
			System.out.println("getSessionFactory() is unavailable");
			return null;
		}
		Session session = getSessionFactory().openSession();
		session.beginTransaction();

		employeeBo.setDocuments(documentBoLst);
		session.saveOrUpdate(employeeBo);

		/*
		 * documentBo.setEmployeeBo(employeeBo);
		 * session.saveOrUpdate(documentBo);
		 */

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

	@Override
	public Document getDocument(Document document) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deleteDocument(Document document) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean updateDocument(Document document) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean uploadDocument(Document document) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	public EmployeeService() {
		// TODO Auto-generated constructor stub
	}
}
