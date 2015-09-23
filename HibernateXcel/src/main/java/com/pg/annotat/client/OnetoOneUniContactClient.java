package com.pg.annotat.client;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.pg.annotat.onetoone.uni.contact.bo.Contact;
import com.pg.annotat.onetoone.uni.contact.bo.Passport;
import com.pg.annotat.utils.HibernateUtil;

public class OnetoOneUniContactClient {

	private static SessionFactory sessionFactory = HibernateUtil
			.getSessionFactory();

	public static void main(String[] args) {

		 //createContact();
		// createPassport();
		deleteContact();
		showContact();
		//showPass();
	}

	private static void showContact() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		try {
			
			List<Contact> contactList = (List<Contact>)session.createQuery("from Contact").list();
			
			System.out.println("contacts "+contactList.toString());
			
			session.getTransaction().commit();
			session.close();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			session.close();
			e.printStackTrace();

		}
	}
	
	private static void showPass() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		try {
			
			List<Passport> passportList = (List<Passport>)session.createQuery("from Passport").list();
			
			System.out.println("Passport "+passportList.toString());
			
			session.getTransaction().commit();
			session.close();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			session.close();
			e.printStackTrace();

		}
	}
	
	private static void createContact() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		try {
Contact contact = new Contact("PERSONAL");
Passport pass = new Passport("4444EFGH");

contact.setPass(pass);

session.saveOrUpdate(contact);
			// session.saveOrUpdate(pass);

			session.getTransaction().commit();
			session.close();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			session.close();
			e.printStackTrace();

		}
	}

	private static void createPassport() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		try {
			Passport pass = new Passport("3333EFGH");

			session.saveOrUpdate(pass);

			session.getTransaction().commit();
			session.close();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			session.close();
			e.printStackTrace();

		}
	}

	private static void deleteContact() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		try {
			Contact contact = new Contact("PERSONAL");
			contact.setContactId(1);

			session.delete(contact);

			session.getTransaction().commit();
			session.close();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			session.close();
			e.printStackTrace();

		}
	}
}
