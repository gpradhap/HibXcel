package com.gs.common.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.exception.ConstraintViolationException;

import com.pg.annotat.utils.HibernateUtil;

public class GenericsStaticDataDao<T> {

	private Class<T> tType;
	private static SessionFactory sessionFactory = HibernateUtil
			.getSessionFactory();

	public GenericsStaticDataDao() {
	}

	public GenericsStaticDataDao( Class<T> tType) {
		this.tType = tType;
	}
	
	public List<T> retrieveAll() {
		List<T> resultList = null;

		Session session = sessionFactory.openSession();
		session.beginTransaction();

		//determining-the-primary-key-property-name-using-hibernate
		String idPropertyName = sessionFactory.getClassMetadata(tType).getIdentifierPropertyName();
		
		try {
			Criteria queryCrit = session.createCriteria(tType);
			queryCrit.addOrder(Order.asc(idPropertyName));
			resultList = queryCrit.list();
			/*attrLookupGroupList = session.createQuery("from AttrLookupGroup")
					.list();*/
			/*if (null != resultList) {
				attrLookupGroupList = (List<AttrLookupGroup>) attrLookupGroupList;
			}*/
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			session.close();
		}
		return resultList;
	}

	public boolean saveOrUpdate(List<T> saveLst) {

		if (null == saveLst || saveLst.size() == 0) {
			return false;
		}
		
		Session session = null;
		//session = sessionFactory.openSession();
		//session.setFlushMode(FlushMode.MANUAL);
		//session.beginTransaction();

		int count = 0;
		for (T iRecord : saveLst) {

			try {
				//if(null ==session || !session.isOpen()){
					session = sessionFactory.openSession();
					session.setFlushMode(FlushMode.MANUAL);
				//}
				//if(null == session.getTransaction() || !session.getTransaction().isActive()){
					session.beginTransaction();					
				//}
				//session.merge(attrLookupGroup);
				session.persist(iRecord);
				count++;
				if (count % 20 == 0) {
					// 20, same as the JDBC batch size
					// flush a batch of inserts and release memory:
					session.flush();
					session.clear();
				}
				session.flush();
				session.getTransaction().commit();
				session.close();
			
			} catch (ConstraintViolationException violationExcep) {
				System.out.println("saveOrUpdate fails "
						+ iRecord.toString() + "\twith exception"
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
	
}
