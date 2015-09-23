package com.pg.annotat.client;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.pg.annotat.onetoone.shareprim.customer.bo.Customer;
import com.pg.annotat.onetoone.shareprim.customer.bo.CustomerDetails;
import com.pg.annotat.onetoone.uni.contact.bo.Contact;
import com.pg.annotat.onetoone.uni.contact.bo.Passport;
import com.pg.annotat.utils.HibernateUtil;

public class OnetoOneSharePrimClient {

	private static SessionFactory sessionFactory = HibernateUtil
			.getSessionFactory();

	public static void main(String[] args) {

		//createCustomer();
		 //createCustomerDetails();
		createCustomerHierarch();
		 //showCustomer();
		//showCustDtl();
		// deleteContact();

	}
	private static void deleteContact() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		try {
			Contact contact = new Contact("PERSONAL");
			contact.setContactId(2);

			session.delete(contact);

			session.getTransaction().commit();
			session.close();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			session.close();
			e.printStackTrace();

		}
	}

	
	private static void showCustomer() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		try {

			List<Customer> contactList = (List<Customer>) session.createQuery(
					"from Customer").list();

			System.out.println("Customer " + contactList.toString());

			session.getTransaction().commit();
			session.close();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			session.close();
			e.printStackTrace();

		}
	}

	private static void showCustDtl() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		try {

			List<CustomerDetails> custDtlList = (List<CustomerDetails>) session
					.createQuery("from CustomerDetails").list();

			System.out.println("CustomerDetails " + custDtlList.toString());

			session.getTransaction().commit();
			session.close();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			session.close();
			e.printStackTrace();

		}
	}

	private static void createCustomerHierarch(){
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		try {
			Customer customer = new Customer("Pradhap_001");
			CustomerDetails custDtl = new CustomerDetails("000119999");
			customer.setCustDtl(custDtl);
			//custDtl.setCustomer(customer);
			
			session.save(customer);

			session.getTransaction().commit();
			session.close();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			session.close();
			e.printStackTrace();

		}
	}

	private static void createCustomer() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		try {
			Customer customer = new Customer("custTempName");

			session.save(customer);

			session.getTransaction().commit();
			session.close();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			session.close();
			e.printStackTrace();

		}
	}
	

	private static void createCustomerDetails() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		try {
			CustomerDetails cDtl = new CustomerDetails("1114445555");
			cDtl.setCustomerId(1);
			//session.save(cDtl);
			session.saveOrUpdate(cDtl);

			System.out.println("show obj "+cDtl);
			
			session.getTransaction().commit();
			session.close();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			session.close();
			e.printStackTrace();

		}
	}

}
