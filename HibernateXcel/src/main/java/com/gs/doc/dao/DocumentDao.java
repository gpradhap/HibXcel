package com.gs.doc.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.gs.base.dao.BaseAbstractDao;
import com.gs.doc.bo.DocumentBo;

public class DocumentDao extends BaseAbstractDao<DocumentBo> {

	
	public DocumentDao() {
	}

	public DocumentDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public List<DocumentBo> createList(DocumentBo obj) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Boolean delete(DocumentBo obj) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<DocumentBo> retrieveList(DocumentBo obj) {
		// TODO Auto-generated method stub
		return null;
	}

}
