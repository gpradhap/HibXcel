package com.pg.client;

import static junit.framework.Assert.assertNotNull;

import org.junit.Test;

import com.pg.stock.bo.Stock;

public class StockClientTest {

	StockClient stockClient;
	
	@Test
	public void getStock(){
		assertNotNull(stockClient);
		//stockClient.getStock();
	}
	
}
