package com.gs.common.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.gs.base.dao.BaseAbstractDao;
import com.gs.common.bo.AttrLookupGroupBo;
import com.gs.common.bo.AttributeLookupBo;
import com.gs.common.service.AttributeLookupService;
import com.gs.doc.bo.DocumentBo;

public class AttributeLookupDao extends
BaseAbstractDao<AttributeLookupBo> {

	public AttributeLookupDao() {
	}

	public AttributeLookupDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
	
	/*@Override
	public AttributeLookupBo create(AttributeLookupBo attributeLookupBo) {
		if(null == attributeLookupBo){return null;}
		
		try{
		SessionFactory sessionFactory = getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		session.saveOrUpdate(attributeLookupBo);
 
		session.getTransaction().commit();
		session.close();
		}catch(Exception excep){
			System.out.println("saveOrupdate fails ?:"+excep.getMessage());	
		}
		
		return null;
	}*/
	
	@Override
	public List<AttributeLookupBo> createList(AttributeLookupBo obj) {
		return null;
	}
	
	@Override
	public List<AttributeLookupBo> retrieveList(AttributeLookupBo attributeLookupBo) {
		if(null == attributeLookupBo){return null;}
		List<AttributeLookupBo> attributeLookupBoList = null; 
		try{
		SessionFactory sessionFactory = getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(AttributeLookupBo.class);
		if(null != attributeLookupBo.getAttrLookupID()){
			criteria.add(Restrictions.eq("attrLookupID",attributeLookupBo.getAttrLookupID()));
		}
		if(null != attributeLookupBo.getAttribute()){
			criteria.add(Restrictions.eq("attribute",attributeLookupBo.getAttribute()));
		}
		
		attributeLookupBoList = criteria.list();		
		
		
		session.getTransaction().commit();
		session.close();
		}catch(Exception excep){
			System.out.println("exception ?:"+excep.getMessage());
		}
		return attributeLookupBoList;
	}
	
	public Set<AttributeLookupBo> retrieveList(AttrLookupGroupBo attrLookupGroupBo) {
		if(null == attrLookupGroupBo){return null;}
		Set<AttributeLookupBo> attributeLookupBoList =  null;
		try{
		SessionFactory sessionFactory = getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		Criteria criteria = session.createCriteria(AttrLookupGroupBo.class);

		if(null != attrLookupGroupBo.getAttrLookupGrpID()){
			criteria.add(Restrictions.eq("attrLookupGrpID",attrLookupGroupBo.getAttrLookupGrpID()));
		}	
		
		if(null != attrLookupGroupBo.getGroupName()){
			criteria.add(Restrictions.eq("groupName",attrLookupGroupBo.getGroupName()));
		}
		
		List<AttrLookupGroupBo> attrLookupGroupBoList =  criteria.list();
		
		if(null != attrLookupGroupBoList && attrLookupGroupBoList.size() >=0){
			if(null != attrLookupGroupBoList.get(0)){
				attributeLookupBoList = ((AttrLookupGroupBo)attrLookupGroupBoList.get(0)).getAttributeLookup();
			}
		}
		
		
		session.getTransaction().commit();
		session.close();
		}catch(Exception excep){
			System.out.println("exception ?:"+excep.getMessage());
		}
		return attributeLookupBoList;
	}
	
	@Override
	protected SessionFactory getSessionFactory() {
		return super.getSessionFactory();
	}


	public Set<DocumentBo> retrieveDocuments(AttrLookupGroupBo attrLookupGroupBo){
		
		if(null == attrLookupGroupBo){return null;}
		
		Set<DocumentBo> documentSet = new HashSet<DocumentBo>();
		
		return documentSet;
	}
}
