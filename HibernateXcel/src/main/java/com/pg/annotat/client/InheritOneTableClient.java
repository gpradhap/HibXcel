package com.pg.annotat.client;

import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.pg.annotat.inherit.onetab.bo.Employee;
import com.pg.annotat.inherit.onetab.bo.Owner;
import com.pg.annotat.inherit.onetab.bo.Person;
import com.pg.annotat.utils.HibernateUtil;

public class InheritOneTableClient {

	private static SessionFactory sessionFactory = HibernateUtil
			.getSessionFactory();

	public static void main(String[] args) {

Employee emp1= new Employee("emp1_fName", "emp1_lName", new Date("1/1/2014"), "IT-DEV");
Employee emp2= new Employee("emp2_fName", "emp2_lName", new Date("1/1/2014"), "IT-TST");
Employee emp3= new Employee("emp3_fName", "emp3_lName", new Date("1/1/2014"), "IT-PROD");

createEmployee(emp1);
createEmployee(emp2);
createEmployee(emp3);

System.out.println("emp1 "+emp1);

Owner own1= new Owner("own1_fName", "own1_lName","IT-DEV");
createOwner(own1);

		System.out.println("own1 "+own1);

		/*Owner own1= new Owner("own1_fName", "own1_lName","IT-DEV");
		own1.setPersonId(4);
		updatePerson(own1);*/
		
		List<Person> persons = fetchAllPerson();
		System.out.println("persons "+persons);
	}

	public static void createEmployee(Employee emp) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		try {
			session.save(emp);

			session.getTransaction().commit();
			session.close();
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			session.close();
		}
	}

	public static void createOwner(Owner own) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		try {
			session.save(own);

			session.getTransaction().commit();
			session.close();
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			session.close();
		}
	}

	public static List<Person> fetchAllPerson() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		List<Person> persons = null;
		try {
			Query query = session.createQuery("from Person");

			persons = (List<Person>) query.list();

			session.getTransaction().commit();
			session.close();
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			session.close();
		}
		return persons;
	}
	
	public static void updatePerson(Person person){

		Session session = sessionFactory.openSession();
		session.beginTransaction();
		try {
			session.update(person);

			session.getTransaction().commit();
			session.close();
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			session.close();
		}
	}
}
