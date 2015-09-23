package com.pg.client;

import com.pg.common.bo.BaseBO;
import com.pg.stock.bo.Stock;
import com.pg.stock.dao.StockDao;
import com.pg.stock.dao.impl.StockDaoImpl;

public class StockClient {

	public static void main(String str[]){
		
		getStock();
		
	}
	
	public static Stock getStock() {
		StockDao stockDao = new StockDaoImpl();

		Stock stock = new Stock();

		stock.setStockCode("4005");
		stock.setStockName("GP_4005");

		stockDao.create(stock);

		BaseBO stockBaseBo = stockDao.get(0, 1);

		return stockBaseBo instanceof Stock ? (Stock) stockBaseBo : null;
	}

}
