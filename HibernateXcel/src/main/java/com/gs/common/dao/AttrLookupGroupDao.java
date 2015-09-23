package com.gs.common.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.SimpleFormatter;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

import com.gs.base.dao.BaseAbstractDao;
import com.gs.base.dao.exception.BaseAbstractDaoException;
import com.gs.common.bo.AttrLookupGroupBo;
import com.gs.common.bo.AttributeLookupBo;
import com.gs.common.dao.exception.AttrLookupGroupDaoException;
import com.gs.common.service.AttributeLookupService;
import com.gs.emp.bo.EmployeeAttributesBo;
import com.pg.annotat.utils.HibernateUtil;

public class AttrLookupGroupDao extends BaseAbstractDao<AttrLookupGroupBo> {

	static Logger log = Logger.getLogger(AttrLookupGroupDao.class);

	/*
	 * private static SessionFactory sessionFactory = HibernateUtil
	 * .getSessionFactory();
	 */

	public AttrLookupGroupDao() {
	}

	public AttrLookupGroupDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	/*public AttrLookupGroupBo create(AttrLookupGroupBo attrLookupGroupBo) {
		if(null == attrLookupGroupBo){return null;}
		
		try{
		SessionFactory sessionFactory = getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		populateDefaults(attrLookupGroupBo);
		
		session.saveOrUpdate(attrLookupGroupBo);
 
		session.getTransaction().commit();
		session.close();
		}catch(Exception excep){
			System.out.println("saveOrupdate fails ?:"+excep.getMessage());	
		}
		
		return null;
	}*/

	public AttrLookupGroupBo retrieveAttrWithEmps(AttrLookupGroupBo attrLookupGroupBo) throws AttrLookupGroupDaoException {
		
		SessionFactory sessionFactory = getSessionFactory();

		if(null == sessionFactory){
			if(log.isDebugEnabled()){
				log.debug("sessionFactory is required. sessionFactory?: " + sessionFactory);
				return null;
			}
		}

		Session session = sessionFactory.openSession();
		
		Set<AttrLookupGroupBo> attrLookupGroupBoSet;
		try{
			attrLookupGroupBoSet = retrieveSet(attrLookupGroupBo, session);
		}catch(BaseAbstractDaoException e){
			e.printStackTrace();
			throw new AttrLookupGroupDaoException(e.getMessage());
		}
		
		if(null != attrLookupGroupBoSet){
			
			for(AttrLookupGroupBo attrLookupGrpBoLo:attrLookupGroupBoSet){
				if(null == attrLookupGrpBoLo){continue;}
				
				Set<AttributeLookupBo> attributeLookupBoSet = attrLookupGrpBoLo.getAttributeLookup();
				
				if(null != attributeLookupBoSet){
					for(AttributeLookupBo attributeLookupBoLo:attributeLookupBoSet){
						if(null == attributeLookupBoLo){continue;}
						
						Set<EmployeeAttributesBo> employeeAttributesBoSet = attributeLookupBoLo.getEmpAttributeSet();
						if(null != employeeAttributesBoSet){
							for(EmployeeAttributesBo employeeAttributesBoLo:employeeAttributesBoSet){
								if(null == employeeAttributesBoLo){continue;}
							}
						}
						
					}
					
				}
				
			}
			
		}
		
		session.close();
		
		if(null != attrLookupGroupBoSet){
			if(attrLookupGroupBoSet.size() > 1){
				log.warn("retrieveAttrWithEmps returns > 1 records;");
			}
			if(attrLookupGroupBoSet.size() >= 1){
				attrLookupGroupBo = attrLookupGroupBoSet.iterator().next();
			}
		}
		
		return attrLookupGroupBo;
	}
	
	private void populateDefaults(AttrLookupGroupBo attrLookupGroupBo) throws ParseException {
		if(attrLookupGroupBo.getExpireDate() == null){
			attrLookupGroupBo.setExpireDate( new SimpleDateFormat().parse("01/01/2999"));
		}
		if(attrLookupGroupBo.getUpdateDate() == null){
			attrLookupGroupBo.setUpdateDate(Calendar.getInstance().getTime());
		}
		if(attrLookupGroupBo.getUpdateBy() == null){
			attrLookupGroupBo.setUpdateBy("N/A");
		}
	}

	@Override
	public List<AttrLookupGroupBo> createList(AttrLookupGroupBo obj) {
		// TODO Auto-generated method stub
		return null;
	}

	/*@Override
	public AttrLookupGroupBo retriveOne(AttrLookupGroupBo attrLookupGroupBo) {
		if(null == attrLookupGroupBo){return null;}
		
		try{
		SessionFactory sessionFactory = getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(AttrLookupGroupBo.class);
		
		if(null != attrLookupGroupBo.getAttrLookupGrpID()){
			criteria.add(Restrictions.eq("attrLookupGrpID", attrLookupGroupBo.getAttrLookupGrpID()));}
		
		if(StringUtils.isNotBlank(attrLookupGroupBo.getGroupName())){
			criteria.add(Restrictions.eq("groupName", attrLookupGroupBo.getGroupName()));}
		
		List<AttrLookupGroupBo> attrLookupGroupBoList = criteria.list();
		
		if(null != attrLookupGroupBoList && attrLookupGroupBoList.size() > 0){
			attrLookupGroupBo = (AttrLookupGroupBo) attrLookupGroupBoList.get(0);
		}
 
		session.getTransaction().commit();
		session.close();
		}catch(Exception excep){
			System.out.println("saveOrupdate fails ?:"+excep.getMessage());	
		}
		
		return attrLookupGroupBo;
	}*/

	public List<AttrLookupGroupBo> retrieveAll() {
		List<AttrLookupGroupBo> attrLookupGroupList = null;

		Session session = getSessionFactory().openSession();
		session.beginTransaction();

		try {
			Criteria attrLookupGroupCrit = session
					.createCriteria(AttrLookupGroupBo.class);
			attrLookupGroupCrit.addOrder(Order.asc("attrLookupGrpID"));
			attrLookupGroupList = attrLookupGroupCrit.list();
			/*
			 * attrLookupGroupList = session.createQuery("from AttrLookupGroup")
			 * .list();
			 */
			if (null != attrLookupGroupList) {
				attrLookupGroupList = (List<AttrLookupGroupBo>) attrLookupGroupList;
			}
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			session.close();
		}
		return attrLookupGroupList;
	}

	public boolean saveOrUpdate(List<AttrLookupGroupBo> attrLookupGroupLst) {
		List<AttrLookupGroupBo> attrLookupGroupList = null;

		if (null == attrLookupGroupLst || attrLookupGroupLst.size() == 0) {
			return false;
		}

		Session session = null;
		// session = sessionFactory.openSession();
		// session.setFlushMode(FlushMode.MANUAL);
		// session.beginTransaction();

		int count = 0;
		for (AttrLookupGroupBo attrLookupGroup : attrLookupGroupLst) {

			try {
				// if(null ==session || !session.isOpen()){
				session = getSessionFactory().openSession();
				session.setFlushMode(FlushMode.MANUAL);
				// }
				// if(null == session.getTransaction() ||
				// !session.getTransaction().isActive()){
				session.beginTransaction();
				// }
				// session.merge(attrLookupGroup);
				session.persist(attrLookupGroup);
				count++;
				if (count % 20 == 0) {
					// 20, same as the JDBC batch size
					// flush a batch of inserts and release memory:
					// session.flush();
					// session.clear();
				}
				session.flush();
				session.getTransaction().commit();
				session.close();

			} catch (ConstraintViolationException violationExcep) {
				System.out.println("saveOrUpdate fails "
						+ attrLookupGroup.toString() + "\twith exception"
						+ violationExcep.getMessage());
				session.getTransaction().rollback();
				session.close();
			} catch (Exception e) {
				e.printStackTrace();
				session.getTransaction().rollback();
				session.close();
				return false;
			}
		}

		/*
		 * if(session.isDirty() && session.isConnected()){ session.flush();
		 * session.getTransaction().commit(); session.close(); }
		 */
		if (null != session && session.isOpen()) {
			session.close();
		}
		return true;
	}

	public boolean statelessInsert(List<AttrLookupGroupBo> attrLookupGroupLst) {
		List<AttrLookupGroupBo> attrLookupGroupList = null;

		if (null == attrLookupGroupLst || attrLookupGroupLst.size() == 0) {
			return false;
		}

		StatelessSession session = getSessionFactory().openStatelessSession();

		session.beginTransaction();

		int count = 0;
		for (AttrLookupGroupBo attrLookupGroup : attrLookupGroupLst) {

			try {

				session.insert(attrLookupGroup);
				session.getTransaction().commit();

			} catch (ConstraintViolationException violationExcep) {
				System.out.println("saveOrUpdate fails "
						+ attrLookupGroup.toString() + "\twith exception"
						+ violationExcep.getMessage());
				// session.getTransaction().rollback();
				// session.close();
			} catch (Exception e) {
				e.printStackTrace();
				session.getTransaction().rollback();
				session.close();
				return false;
			}
		}

		/*
		 * if(session.isDirty() && session.isConnected()){ session.flush();
		 * session.getTransaction().commit(); session.close(); }
		 */
		session.close();

		return true;
	}

}
