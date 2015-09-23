package com.gs.doc.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.criterion.Order;
import org.hibernate.exception.ConstraintViolationException;

import com.gs.base.dao.BaseAbstractDao;
import com.gs.common.bo.AttrLookupGroupBo;
import com.gs.doc.bo.DocumentAttributesBo;

public class DocumentAttributesDaoImpl extends
		BaseAbstractDao<DocumentAttributesBo> {

	@Override
	public DocumentAttributesBo create(DocumentAttributesBo documentAttributesBo) {

		if(null == documentAttributesBo){
			return null;
		}

		Session session = getSessionFactory().openSession();
		session.beginTransaction();

		try {

			if(null != documentAttributesBo){
				session.saveOrUpdate(documentAttributesBo);
			}

			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			session.close();
		}

		return null;
	}

	@Override
	public List<DocumentAttributesBo> createList(DocumentAttributesBo obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean delete(DocumentAttributesBo obj) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<DocumentAttributesBo> retrieveList(DocumentAttributesBo obj) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public DocumentAttributesBo retrieveOne(DocumentAttributesBo obj) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public DocumentAttributesDaoImpl() {
	}

	public DocumentAttributesDaoImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

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
