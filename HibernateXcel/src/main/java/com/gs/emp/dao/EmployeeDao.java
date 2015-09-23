package com.gs.emp.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.gs.base.dao.BaseAbstractDao;
import com.gs.common.bo.AttrLookupGroupBo;
import com.gs.common.bo.AttributeLookupBo;
import com.gs.common.dao.AttrLookupGroupDao;
import com.gs.doc.bo.DocumentBo;
import com.gs.emp.bo.EmployeeAttributesBo;
import com.gs.emp.bo.EmployeeBo;

public class EmployeeDao extends BaseAbstractDao<EmployeeBo> {

	private static final Logger log = Logger.getLogger(EmployeeDao.class);

	public EmployeeDao() {}

	public EmployeeDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
	@Override
	public List<EmployeeBo> createList(EmployeeBo obj) {
		return null;
	}
	
	@Override
	public Boolean delete(EmployeeBo obj) {
		return null;
	}
	
	public EmployeeBo retrieveEmployeeWithAttr(EmployeeBo inEmployeeBo){
		
		if(null == inEmployeeBo || null == inEmployeeBo.getEmployeeId()){
			return null;
		}

		Session session = getSession();
		session.beginTransaction();
		EmployeeBo employeeBo = null;
		Criteria empCriteria = session.createCriteria(EmployeeBo.class);
		empCriteria.add(Restrictions.eq("employeeId",inEmployeeBo.getEmployeeId()));
		empCriteria.createAlias("empAttributeSet", "empAttr");
		empCriteria.createAlias("empAttributeSet.employeeAttributesBoID.attributeLookup", "attrLookup");
		//empCriteria.createAlias("attrLookup.attrLookupGroup", "attrLookupGrp");
		
		//attributeLookup
		
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("employeeId"));
		projectionList.add(Projections.property("firstName"));
		projectionList.add(Projections.property("lastName"));

		//projectionList.add(Projections.property("attrLookupGrp.groupName"));
		
		projectionList.add(Projections.property("attrLookup.displayOrder"));
		projectionList.add(Projections.property("attrLookup.attrDesc"));
		
		projectionList.add(Projections.property("empAttr.attributeName"));
		projectionList.add(Projections.property("empAttr.attributeValue"));
		
		empCriteria.setProjection(projectionList);
		empCriteria.setFetchMode("empAttr", org.hibernate.FetchMode.EAGER);
		empCriteria.setFetchMode("attrLookup", org.hibernate.FetchMode.EAGER);
		//empCriteria.setFetchMode("attrLookupGrp", org.hibernate.FetchMode.EAGER);

		List<Object> empBoList = empCriteria.list();
		
		if(null != empBoList){
			System.out.println(" list size "+empBoList.size());
		}
		
		for(Object data:empBoList){
			Object[] projecObjArr = (Object[]) data;
			int count = 0;
			for(Object projecObj:projecObjArr){
				System.out.print(" projecObjArr["+count+"] "+projecObj);
				count++;
			}
			System.out.println("");
		}
		
		return employeeBo;
	}
	
	public EmployeeBo addEmployeeAttributes(EmployeeBo inEmployeeBo, AttrLookupGroupBo inAttrLookupGroupBo) {
		

		if(null == inEmployeeBo || null == inAttrLookupGroupBo || null == inEmployeeBo.getEmpAttributeSet()){
			return null;
		}
		
		//SessionFactory session = getSessionFactory();
		Session session = getSession();
		session.beginTransaction();
		EmployeeBo employeeBo = null;
		try{
			employeeBo = getOneRecord(session, EmployeeBo.class, inEmployeeBo.getEmployeeId());
		}catch(Exception e){
			e.printStackTrace();
		}

		System.out.println(" employeeBo ?? " + employeeBo.toString());

		AttrLookupGroupBo attrLookupGroupBo = null;
		try{
			//attrLookupGroupBo = (AttrLookupGroupBo)session.get(AttrLookupGroupBo.class,new Integer(18));
			attrLookupGroupBo = new AttrLookupGroupDao().getOneRecord(session,
					AttrLookupGroupBo.class, inAttrLookupGroupBo.getAttrLookupGrpID());
		}catch(Exception e){
			e.printStackTrace();
		}

		if(null == attrLookupGroupBo){
			System.out.println(" no record returns " + attrLookupGroupBo);
			return null;
		}

		Set<EmployeeAttributesBo> empAttributeSet = new HashSet<EmployeeAttributesBo>();

		Date expireDate = null;
		try{
			expireDate = new SimpleDateFormat("MM/dd/yyyy").parse("01/01/2099");
		}catch(ParseException e){
			e.printStackTrace();
		}
		
		Set<EmployeeAttributesBo> inEmployeeAttributesBoSet = inEmployeeBo.getEmpAttributeSet(); 
		
		for(AttributeLookupBo attributeLookupLo : attrLookupGroupBo.getAttributeLookup()){
			if(null != attributeLookupLo){
				EmployeeAttributesBo employeeAttributes = new EmployeeAttributesBo();
				employeeAttributes.setAttributeLookup(attributeLookupLo);
				employeeAttributes.setEmployee(employeeBo);
				employeeAttributes.setUpdateBy(inEmployeeBo.getUpdateBy());
				employeeAttributes.setUpdateDate(Calendar.getInstance().getTime());
				employeeAttributes.setExpireDate(expireDate);
				employeeAttributes.setCreateBy(inEmployeeBo.getUpdateBy());
				
				String attrLookupStr = attributeLookupLo.getAttribute();
				
				for(EmployeeAttributesBo inEmployeeAttributeLo : inEmployeeAttributesBoSet){

					if(StringUtils.isNotBlank(attrLookupStr)
							&& attrLookupStr.equals(inEmployeeAttributeLo.getAttributeName())){
						employeeAttributes.setAttributeName(attrLookupStr);
						employeeAttributes.setAttributeDesc(attrLookupStr);
						employeeAttributes.setAttributeValue(inEmployeeAttributeLo
								.getAttributeValue());
						break;
					}
				}
				empAttributeSet.add(employeeAttributes);
				employeeBo.setEmpAttributeSet(empAttributeSet);
				attributeLookupLo.setEmpAttributeSet(empAttributeSet);
			}
		}


		session.getTransaction().commit();
		//session.close();
		
		System.out.println(" new employeeBo ? " + employeeBo.toString());
		return null;
	}

	public void addEmployeeAttributes() {
		
		//SessionFactory session = getSessionFactory();
		Session session = getSession();
		session.beginTransaction();
		
		//EmployeeBo employeeBo = new EmployeeDao(getSessionFactory()).retrieveOne(new EmployeeBo(6),session);
		EmployeeBo employeeBo = null;
		try{
			employeeBo = (EmployeeBo)session.get(EmployeeBo.class, new Integer(6));
		}catch(Exception e){
			e.printStackTrace();
		}

		//System.out.println(" employeeBo ?? " + employeeBo.toString());

		AttrLookupGroupBo attrLookupGroupBo = null;
		try{
			attrLookupGroupBo = (AttrLookupGroupBo)session.get(AttrLookupGroupBo.class,new Integer(18));
		}catch(Exception e){
			e.printStackTrace();
		}

		if(null == attrLookupGroupBo){
			System.out.println(" no record returns " + attrLookupGroupBo);
			return;
		}

		Set<EmployeeAttributesBo> empAttributeSet = new HashSet<EmployeeAttributesBo>();
		//Set<EmployeeAttributesBo> empAttributeSet = employeeBo.getEmpAttributeSet();

		Date expireDate = null;
		try{
			expireDate = new SimpleDateFormat("MM/dd/yyyy").parse("01/01/2099");
		}catch(ParseException e){
			e.printStackTrace();
		}
		
		for(AttributeLookupBo attributeLookupLo : attrLookupGroupBo.getAttributeLookup()){
			if(null != attributeLookupLo){
				EmployeeAttributesBo employeeAttributes = new EmployeeAttributesBo();
				employeeAttributes.setAttributeLookup(attributeLookupLo);
				employeeAttributes.setEmployee(employeeBo);
				employeeAttributes.setUpdateBy("PG");
				employeeAttributes.setUpdateDate(Calendar.getInstance().getTime());
				employeeAttributes.setExpireDate(expireDate);
				employeeAttributes.setCreateBy("PG");
				if("ADDRESS1".equals(attributeLookupLo.getAttribute())){
					employeeAttributes.setAttributeDesc("ADDRESS1");
					employeeAttributes.setAttributeValue("9700 NW 112th Ave");

				}else if("ADDRESS2".equals(attributeLookupLo.getAttribute())){
					employeeAttributes.setAttributeDesc("ADDRESS2");
					employeeAttributes.setAttributeValue("");
				}else if("CITY".equals(attributeLookupLo.getAttribute())){
					employeeAttributes.setAttributeDesc("CITY");
					employeeAttributes.setAttributeValue("Miami");
				}else if("STATE".equals(attributeLookupLo.getAttribute())){
					employeeAttributes.setAttributeDesc("STATE");
					employeeAttributes.setAttributeValue("Florida");
				}else if("ZIP".equals(attributeLookupLo.getAttribute())){
					employeeAttributes.setAttributeDesc("ZIP");
					employeeAttributes.setAttributeValue("33178");
				}
				empAttributeSet.add(employeeAttributes);
				employeeBo.setEmpAttributeSet(empAttributeSet);
				attributeLookupLo.setEmpAttributeSet(empAttributeSet);
			}
		}

		/*//employeeBo.setDocuments(null);
		//employeeBo.setEmpAttributeSet(empAttributeSet);
		System.out.println(" employee ? " + employeeBo.toString());

		try{
			//employeeBo = new EmployeeDao(getSessionFactory()).create(employeeBo,session);

		}catch(Exception e){
			log.error("EXCEPTION: creating employee failed " + e.getMessage());
			session.getTransaction().rollback();
			session.close();
			return;
		}*/

		session.getTransaction().commit();
		//session.close();
		
		System.out.println(" new employeeBo ? " + employeeBo.toString());
		return;
	}
	
	/*@Override
	public EmployeeBo retriveOne(EmployeeBo employeeBo) {
		
		if(null == employeeBo){return employeeBo;}
		
		SessionFactory sessionFactory = getSessionFactory();
		Session session = sessionFactory.openSession();
		
		Criteria criteria = session.createCriteria(EmployeeBo.class);
		
		if(null != employeeBo.getEmployeeId() && 0 != employeeBo.getEmployeeId()){
		criteria.add(Restrictions.eq("employeeId", value))
		}
		
		
		session.close();
		return null;
	}*/
	

}
