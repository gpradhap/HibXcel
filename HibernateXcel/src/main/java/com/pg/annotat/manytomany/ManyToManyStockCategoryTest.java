package com.pg.annotat.manytomany;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.pg.annotat.utils.HibernateUtil;

public class ManyToManyStockCategoryTest {

	public static void main(String... str) {

		//createStock();
		retrieveCategory();

	}

	private static void retrieveCategory(){
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		Category category = new Category();
		category.setCategoryDesc("3D TV");
		
		Criteria crit = session.createCriteria(Category.class);
		crit.add(Restrictions.eq("categoryDesc", "3D TV"));
		//crit.list();
		List<Category> categoryLst = crit.list();
		//List<Category> categoryLst =null;
	
		if(null != categoryLst && categoryLst.size() > 0){
			System.out.println("categoryLst.size: "+categoryLst.size());
			Category category2 = categoryLst.get(0);
			
			category2.toString();
			
		}
		
		session.getTransaction().commit();
		session.close();
		

		

	}
	
	private static void createStock() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Category category1 = new Category("MODEL", "TV Model is LED");
		Category category2 = new Category("SIZE", "46 inch size");
		Category category3 = new Category("TECH", "3D TV");

		//Category categoryLg = new Category("BRAND", "LG");
		/*List<Category> categoryListLg = new ArrayList<Category>();
		categoryListLg.add(category1);
		categoryListLg.add(category2);
		categoryListLg.add(category3);
		categoryListLg.add(categoryLg);*/

		Category categorySam = new Category("BRAND", "SAMSUNG");
		List<Category> categoryListSS = new ArrayList<Category>();
		categoryListSS.add(category1);
		categoryListSS.add(category2);
		categoryListSS.add(category3);
		categoryListSS.add(categorySam);

		//Stock stock1 = new Stock("LG", "LG46L3D1");
		//Stock stock2 = new Stock("LG", "LG46L3D2");
		//Stock stock3 = new Stock("SAMSUNG", "SAM46L3D1");
		Stock stock4 = new Stock("SAMSUNG", "SAM46L3D2");

		//stock1.setCategories(categoryListLg);
		//stock2.setCategories(categoryListLg);

		//stock3.setCategories(categoryListSS);
		stock4.setCategories(categoryListSS);

		session.save(stock4);
		
		session.getTransaction().commit();
		session.close();
	}

}
