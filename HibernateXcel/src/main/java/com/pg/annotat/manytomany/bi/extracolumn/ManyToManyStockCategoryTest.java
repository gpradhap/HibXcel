package com.pg.annotat.manytomany.bi.extracolumn;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.pg.annotat.utils.HibernateUtil;

public class ManyToManyStockCategoryTest {

	public static void main(String... str) {

		//createStock();
		retrieveCategory();
		//updateStock();
		

	}
	
	private static void updateStock() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Category category = null;
		// category.setCategoryDesc("category1");

		Criteria crit = session.createCriteria(Category.class);
		// crit.add(Restrictions.eq("categoryDesc", "category1"));
		List<Category> categoryLst = crit.list();

		
		//With Existing Stock
		Criteria critStock = session.createCriteria(Stock.class);
		critStock.add(Restrictions.eq("stockCode", "S003"));
		List<Stock> stockLst = critStock.list();
		Stock stock = null;
		if(null != stockLst && stockLst.size() >0){
			stock = (Stock)stockLst.get(0);
		}
		
		/*
		//with new stock
		Stock stock = new Stock("S003", "BUS STOCK");
		session.save(stock);
		*/
		
		if(null != categoryLst && categoryLst.size() > 0){
			System.out.println("categoryLst.size: " + categoryLst.size());

			Set<StockCategoryExtn> stockExtnSet = new HashSet<StockCategoryExtn>();
			for(Category categoryLo : categoryLst){

				if(null == categoryLo){continue;}
				StockCategoryExtn stockExtn = new StockCategoryExtn();
				stockExtn.setCreatedDate(Calendar.getInstance().getTime());
				//stockExtn.setStock(stock);
				//stockExtn.setCategory(categoryLo);
				if("AVAIL".equals(categoryLo.getCategoryName())){
					stockExtn.setCreatedBy("S003 AVAIL3");
				}else if("COUNT".equals(categoryLo.getCategoryName())){
					stockExtn.setCreatedBy("S003 COUNT3");
				}else if("IS_HOME_STOCK".equals(categoryLo.getCategoryName())){
					stockExtn.setCreatedBy("S003 IS_HOME_STOCK3");
				}
				
				stockExtnSet.add(stockExtn);
				categoryLo.setStockCategoryExtn(stockExtnSet);
				stock.setStockCategoryExtn(stockExtnSet);
				/*Set<StockCategoryExtn> scExtn = categoryLo.getStockCategoryExtn();
				
				if(null != scExtn){
					for(StockCategoryExtn scxtn : scExtn){
						if(null != scxtn){
							//scxtn.setCreatedBy("Update_GP");
							Stock st = scxtn.getStock();
							System.out.println(" stock's " + (null != st ? st.toString() : null));
						}
					}
				}*/
			}

		}
		//session.save(stock1);
		
		session.getTransaction().commit();
		session.close();
	}

	private static void retrieveCategory(){
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		Category category = new Category();
		//category.setCategoryDesc("category1");
		
		Criteria crit = session.createCriteria(Category.class);
		//crit.add(Restrictions.eq("categoryDesc", "category1"));
		//crit.list();
		List<Category> categoryLst = crit.list();
		//List<Category> categoryLst =null;
	
		
		
		if(null != categoryLst && categoryLst.size() > 0){
			System.out.println("categoryLst.size: "+categoryLst.size());
			Category category2 = categoryLst.get(0);
			Set<StockCategoryExtn> scExtn = category2.getStockCategoryExtn();
			
			//if(null != scExtn && scExtn.size() > 0){
			if(null != scExtn){
				for(StockCategoryExtn scxtn:scExtn){
					if(null != scxtn){
						Stock st = scxtn.getStock();
						System.out.println(" stock's "+ (null != st ? st.toString():null));
					}
				}
			}
			
			//category2.toString();
			
		}

		session.close();

	}
	
	private static void createStock() {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			Session session = sessionFactory.openSession();
			session.beginTransaction();

			try{

			//Stock stock = populateStock();
			
			Stock stock1 = new Stock();
			stock1.setStockCode("S001");
			stock1.setStockName("AVAIL");
			
			Category category1 = new Category();
			category1.setCategoryDesc("Sony TV Brand");
			category1.setCategoryName("SONY");
			
			session.save(category1);

			StockCategoryExtn categoryExtn = new StockCategoryExtn();
			categoryExtn.setCreatedBy("GP");
			categoryExtn.setCreatedDate(Calendar.getInstance().getTime());
			categoryExtn.setStock(stock1);
			categoryExtn.setCategory(category1);
			
			stock1.getStockCategoryExtn().add(categoryExtn);
			//category1.getStockCategoryExtn().add(categoryExtn);
			
			session.save(stock1);
			
			session.getTransaction().commit();
			session.close();
		}catch(Exception e){
			if(null != session.getTransaction()){
				session.getTransaction().rollback();
			}
			session.close();
		}
	}

	private static Stock populateStock() {
		Category category1 = new Category("MODEL", "TV Model is LED");
		Category category2 = new Category("SIZE", "46 inch size");
		Category category3 = new Category("TECH", "2D TV");

		//Category categoryLg = new Category("BRAND", "LG");
		/*List<Category> categoryListLg = new ArrayList<Category>();
		categoryListLg.add(category1);
		categoryListLg.add(category2);
		categoryListLg.add(category3);
		categoryListLg.add(categoryLg);*/

		/*Category categorySam = new Category("BRAND", "SAMSUNG");
		List<Category> categoryListSS = new ArrayList<Category>();
		categoryListSS.add(category1);
		categoryListSS.add(category2);
		categoryListSS.add(category3);
		categoryListSS.add(categorySam);*/

		//Stock stock1 = new Stock("LG", "LG46L3D1");
		//Stock stock2 = new Stock("LG", "LG46L3D2");
		//Stock stock3 = new Stock("SAMSUNG", "SAM46L3D1");
		Stock stock4 = new Stock("S46L2D1","SONY");
		

		StockCategoryExtn stockCategoryExtn1 = new StockCategoryExtn();
		stockCategoryExtn1.setCategory(category1);
		stockCategoryExtn1.setCreatedBy("createdBy_PG");
		stockCategoryExtn1.setCreatedDate(Calendar.getInstance().getTime());
		stockCategoryExtn1.setStock(stock4);
		
		/*StockCategoryExtn stockCategoryExtn2 = new StockCategoryExtn();
		stockCategoryExtn2.setCategory(category2);
		stockCategoryExtn2.setCreatedBy("createdBy_PG");
		stockCategoryExtn2.setCreatedDate(Calendar.getInstance().getTime());
		stockCategoryExtn2.setStock(stock4);

		StockCategoryExtn stockCategoryExtn3 = new StockCategoryExtn();
		stockCategoryExtn3.setCategory(category3);
		stockCategoryExtn3.setCreatedBy("createdBy_PG");
		stockCategoryExtn3.setCreatedDate(Calendar.getInstance().getTime());
		stockCategoryExtn3.setStock(stock4);*/

		Set<StockCategoryExtn> stockCategoryExtnSet = new HashSet<StockCategoryExtn>();
		stockCategoryExtnSet.add(stockCategoryExtn1);
		/*stockCategoryExtnSet.add(stockCategoryExtn2);
		stockCategoryExtnSet.add(stockCategoryExtn3);*/
		

		//stock1.setCategories(categoryListLg);
		//stock2.setCategories(categoryListLg);

		//stock3.setCategories(categoryListSS);
		//stock4.setCategories(categoryListSS);
		category1.setStockCategoryExtn(stockCategoryExtnSet);
		stock4.setStockCategoryExtn(stockCategoryExtnSet);
		return stock4;
	}

}
