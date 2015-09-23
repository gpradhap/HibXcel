package com.gs.emp.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.criterion.Order;
import org.hibernate.exception.ConstraintViolationException;

import com.gs.common.bo.AttrLookupGroupBo;
import com.pg.annotat.utils.HibernateUtil;

public class AttrLookupGroupDao {

	private static SessionFactory sessionFactory = HibernateUtil
			.getSessionFactory();

	public AttrLookupGroupDao() {
	}

	public static List<AttrLookupGroupBo> retrieveAll() {
		List<AttrLookupGroupBo> attrLookupGroupList = null;

		Session session = sessionFactory.openSession();
		session.beginTransaction();

		try {
			Criteria attrLookupGroupCrit = session.createCriteria(AttrLookupGroupBo.class);
			attrLookupGroupCrit.addOrder(Order.asc("attrLookupGrpID"));
			attrLookupGroupList = attrLookupGroupCrit.list();
			/*attrLookupGroupList = session.createQuery("from AttrLookupGroup")
					.list();*/
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

	public static boolean saveOrUpdate(List<AttrLookupGroupBo> attrLookupGroupLst) {
		List<AttrLookupGroupBo> attrLookupGroupList = null;

		if (null == attrLookupGroupLst || attrLookupGroupLst.size() == 0) {
			return false;
		}
		
		Session session = null;
		//session = sessionFactory.openSession();
		//session.setFlushMode(FlushMode.MANUAL);
		//session.beginTransaction();

		int count = 0;
		for (AttrLookupGroupBo attrLookupGroup : attrLookupGroupLst) {

			try {
				//if(null ==session || !session.isOpen()){
					session = sessionFactory.openSession();
					session.setFlushMode(FlushMode.MANUAL);
				//}
				//if(null == session.getTransaction() || !session.getTransaction().isActive()){
					session.beginTransaction();					
				//}
				//session.merge(attrLookupGroup);
				session.persist(attrLookupGroup);
				count++;
				if (count % 20 == 0) {
					// 20, same as the JDBC batch size
					// flush a batch of inserts and release memory:
					//session.flush();
					//session.clear();
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

		/*if(session.isDirty() && session.isConnected()){
			session.flush();
			session.getTransaction().commit();
			session.close();
		}*/
		if(null !=session && session.isOpen()){
			session.close();	
		}
		return true;
	}
	
	public static boolean statelessInsert(List<AttrLookupGroupBo> attrLookupGroupLst) {
		List<AttrLookupGroupBo> attrLookupGroupList = null;

		if (null == attrLookupGroupLst || attrLookupGroupLst.size() == 0) {
			return false;
		}
		
		StatelessSession session = sessionFactory.openStatelessSession();

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
				 //session.getTransaction().rollback();
				 //session.close();
			} catch (Exception e) {
				e.printStackTrace();
				session.getTransaction().rollback();
				session.close();
				return false;
			}
		}

		/*if(session.isDirty() && session.isConnected()){
			session.flush();
			session.getTransaction().commit();
			session.close();
		}*/
		session.close();	

		return true;
	}

}
