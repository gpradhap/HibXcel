package com.pg.annotat.client;

import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.pg.annotat.onetoone.product.bo.Product;
import com.pg.annotat.onetoone.product.bo.ProductExtn;
import com.pg.annotat.utils.HibernateUtil;

public class OnetooneProductClient {

	private static SessionFactory sessionFactory = HibernateUtil
			.getSessionFactory();

	public static void main(String[] args) {

		Session session = sessionFactory.openSession();
		session.beginTransaction();

		try {
			Product prod = new Product("prodName1", "prodDes1");
			ProductExtn prodExt = new ProductExtn("prodCat1", new Date(112014));

			prod.setProductExtn(prodExt);

			prodExt.setProduct(prod);
			
			session.saveOrUpdate(prod);

			session.getTransaction().commit();
			session.close();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			session.close();
			e.printStackTrace();

		}
	}

}
