package com.pg.annotat.client;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.pg.annotat.stock.bo.Stock;
import com.pg.annotat.utils.HibernateUtil;

public class StockClient {

	private static SessionFactory sessionFactory = HibernateUtil
			.getSessionFactory();

	public static void main(String str[]) {

		Stock stockNew = new com.pg.annotat.stock.bo.Stock("102", "102");// Object
																			// state:
																			// Transient
		//createStock(stockNew);

		List<Stock> stocks = getStockAll();
		System.out.println(stocks.toString());

		Stock loLoadStock = new Stock();
		loLoadStock.setStockId(10); 
		loLoadStock = loadStock(loLoadStock);
		
		System.out.println("loadStock "+loLoadStock.getStockName());


		Stock loStock = new Stock();
		loStock.setStockId(10); 
		loStock = getStock(loStock);
		
		System.out.println("getStock "+loStock);

		loStock.setStockName("Updatedname");
		updateStock(loStock);
		System.out.println("getStock "+loStock);
		
		Stock loUpdateStock = new Stock();
		loUpdateStock.setStockCode("111");
		loUpdateStock.setStockName("new Name");
		
		updateStock(loUpdateStock);
		System.out.println("getStock "+loUpdateStock);
	}

	public static List<Stock> getStockAll() {

		List<Stock> stocks = null;
		if (null != sessionFactory) {
			Session session = sessionFactory.openSession();
			Query query = session.createQuery("from Stock");
			stocks = (List<Stock>) query.list();
			session.close();
		}
		return stocks;
	}

	public static Stock getStock(Stock stock) {
		if (null != sessionFactory) {
			Session session = sessionFactory.openSession();
			try {
				stock = (Stock)session.get(Stock.class, stock.getStockId());
				session.close();
			} catch (Exception e) {
				e.printStackTrace();
				session.close();
			}
		}
		return stock;
	}

	public static Stock loadStock(Stock stock) {
		Stock returnStock = null;
		if (null != sessionFactory) {
			Session session = sessionFactory.openSession();
			try {
				//session.load(returnStock, stock.getStockId());
				returnStock = (Stock)session.load(Stock.class, 10);
				if(null != returnStock){
					System.out.println("inside loadStock stockName "+returnStock.getStockName());
				}
				session.close();
			} catch (Exception e) {
				e.printStackTrace();
				session.close();
			}
		}
		return returnStock;
	}
	
	public static void createStock(Stock stock) {
		if (null != sessionFactory) {
			Session sess = sessionFactory.openSession();
			sess.beginTransaction();
			try {
				// at this level stock Object state: Transient
				sess.save(stock);
				sess.getTransaction().commit();
				sess.close();
			} catch (Exception e) {
				sess.getTransaction().rollback();
				sess.close();

			}
		}
	}
	
	public static void updateStock(Stock stock){
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		try{

			session.saveOrUpdate(stock);
		
		session.getTransaction().commit();
		session.close();
		}catch(Exception e){
			session.getTransaction().rollback();
			session.close();
		}
	}
}
