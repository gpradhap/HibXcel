package com.pg.stock.dao.impl;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.pg.common.bo.BaseBO;
import com.pg.stock.bo.Stock;
import com.pg.stock.dao.StockDao;
import com.pg.utils.HibernateUtil;

public class StockDaoImpl implements StockDao {

	@Inject
	HibernateUtil hibernateUtil;
	private SessionFactory sessionFactory = hibernateUtil.getSessionFactory();

	public StockDaoImpl() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean create(BaseBO stockBaseBo) {
		
		boolean createFlag = false;
		
		if(null == sessionFactory){
			return false;
		}
		
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();		
		try {

			
			Stock stock = null;
			if (stockBaseBo instanceof Stock) {
				stock = (Stock) stockBaseBo;
			} else {
				return false;
			}
			session.save(stock);

			transaction.commit();
			createFlag = true;
		} catch (Exception e) {
			session.close();
			transaction.rollback();
			createFlag = false;
		}
		return createFlag;
	}

	@Override
	public boolean delete() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Stock get(int start, int count) {
		
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		Stock stock = (Stock) session.load(Stock.class, new Integer(count));
		transaction.commit();
		session.close();
		
		return stock;
	}

	@Override
	public List<BaseBO> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update() {
		// TODO Auto-generated method stub
		return false;
	}

}
