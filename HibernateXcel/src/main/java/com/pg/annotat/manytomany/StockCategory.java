package com.pg.annotat.manytomany;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="stock_category")
public class StockCategory {

	@ManyToMany(mappedBy="stockId")
	@Column(name="stock_id")
	private int stockId;
	
	@Column(name="cat_id")
	private int categoryId;

	public int getStockId() {
		return stockId;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setStockId(int stockId) {
		this.stockId = stockId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	
	
}
