package com.pg.annotat.client;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.pg.annotat.stock.bo.Stock;
import com.pg.annotat.stock.bo.StockDailyRecord;
import com.pg.annotat.utils.HibernateUtil;

public class StockDailyRecordNStockClient {

	private static SessionFactory sessionFactory = HibernateUtil
			.getSessionFactory();

	public static void main(String str[]) {

		createStock();
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
				stock = (Stock) session.get(Stock.class, stock.getStockId());
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
				// session.load(returnStock, stock.getStockId());
				returnStock = (Stock) session.load(Stock.class, 10);
				if (null != returnStock) {
					System.out.println("inside loadStock stockName "
							+ returnStock.getStockName());
				}
				session.close();
			} catch (Exception e) {
				e.printStackTrace();
				session.close();
			}
		}
		return returnStock;
	}

	public static void createStock() {
		if (null != sessionFactory) {
			Session sess = sessionFactory.openSession();
			sess.beginTransaction();
			try {

				Stock stock = new Stock("SC_003", "bank");

				StockDailyRecord stockD1 = new StockDailyRecord(stock, 100.12f,
						111.10f, new Date("1/1/2000"));
				StockDailyRecord stockD2 = new StockDailyRecord(stock, 90.12f,
						91.10f, new Date("1/1/2014"));
				StockDailyRecord stockD3 = new StockDailyRecord(stock, 80.12f,
						81.10f, new Date("1/1/2015"));

				Set<StockDailyRecord> stockDailyRecord = new HashSet<StockDailyRecord>();
				stockDailyRecord.add(stockD1);
				stockDailyRecord.add(stockD2);
				stockDailyRecord.add(stockD3);

				stock.setStockDailyRecord(stockDailyRecord);

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

	public static void updateStock(Stock stock) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		try {

			session.saveOrUpdate(stock);

			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			session.getTransaction().rollback();
			session.close();
		}
	}
}
