package com.pg.annotat.client;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.pg.annotat.onetoMany.depart.bo.DepartStore;
import com.pg.annotat.onetoMany.depart.bo.Department;
import com.pg.annotat.onetoone.uni.contact.bo.Contact;
import com.pg.annotat.onetoone.uni.contact.bo.Passport;
import com.pg.annotat.utils.HibernateUtil;

public class OneToManyDepartClient {

	private static SessionFactory sessionFactory = HibernateUtil
			.getSessionFactory();

	public static void main(String[] args) {

		createDepartment();
		// createPassport();
		// deleteContact();
		// showContact();
		// showPass();
	}

	private static void showContact() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		try {

			List<Contact> contactList = (List<Contact>) session.createQuery(
					"from Contact").list();

			System.out.println("contacts " + contactList.toString());

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

			List<Passport> passportList = (List<Passport>) session.createQuery(
					"from Passport").list();

			System.out.println("Passport " + passportList.toString());

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

	private static void createDepartment() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		try {
			Department department = new Department("MATHS");
			
			DepartStore storeA = new DepartStore("mthA","NY, USA");
			DepartStore storeB = new DepartStore("mthB","MD, USA");
			DepartStore storeC = new DepartStore("mthC","FL, USA");
			Set<DepartStore> deptStores = new HashSet<DepartStore>();
			storeA.setDepartment(department);
			storeB.setDepartment(department);
			storeC.setDepartment(department);
			
			deptStores.add(storeA);
			deptStores.add(storeB);
			deptStores.add(storeC);
			
			
			department.setDeptStores(deptStores);
			
			session.saveOrUpdate(department);
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

}
