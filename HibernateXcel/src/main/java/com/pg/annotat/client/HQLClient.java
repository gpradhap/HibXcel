package com.pg.annotat.client;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.pg.annotat.onetoone.uni.contact.bo.Contact;
import com.pg.annotat.onetoone.uni.contact.bo.Passport;
import com.pg.annotat.stock.bo.Stock;
import com.pg.annotat.utils.HibernateUtil;

public class HQLClient {

	private static SessionFactory sessionFactory = HibernateUtil
			.getSessionFactory();

	public static void main(String[] args) {

		Contact contact = getContact("1");
		//delete(contact);
		deleteContact(contact);

	}

	public static Contact getContact(String id) {
		Contact contact = null;
		if (null != sessionFactory) {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			try {

				Query query = session
						.createQuery("from Contact where contactId = :id");
				query.setParameter("id", Integer.parseInt(id));
				contact = (Contact) query.uniqueResult();

				session.getTransaction().commit();
				session.close();

			} catch (Exception e) {
				e.printStackTrace();
				session.getTransaction().rollback();
				session.close();
			}
		}
		return contact;
	}

	public static void delete(Object obj) {
		Contact contact = null;
		if (null != sessionFactory) {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			try {

				session.delete(obj);

				session.getTransaction().commit();
				session.close();

			} catch (Exception e) {
				e.printStackTrace();
				session.getTransaction().rollback();
				session.close();
			}
		}
		return;
	}

	public static void deleteContact(Contact contact) {
		if (null != sessionFactory) {
			Session session = sessionFactory.openSession();
			System.out.println("isAct "+session.getTransaction().isActive());
			session.beginTransaction();
			try {

				//session.delete("Passport", contact.getPass());
				//contact.setPass(null);
				//session.update(contact);
				session.delete("contact",contact);

				session.getTransaction().commit();
				session.close();

			} catch (Exception e) {
				e.printStackTrace();
				session.getTransaction().rollback();
				session.close();
			}
		}
		return;
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
}
