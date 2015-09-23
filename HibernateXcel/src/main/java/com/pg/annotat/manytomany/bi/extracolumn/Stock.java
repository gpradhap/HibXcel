package com.pg.annotat.manytomany.bi.extracolumn;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.gs.base.bo.BaseAbstractBo;

@Entity
@Table(name = "stock")
public class Stock implements Serializable {
	private int stockId;
	private String stockCode;
	private String stockName;
	private Set<StockCategoryExtn> stockCategoryExtn = new HashSet<StockCategoryExtn>(0);

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "stock_id")
	public int getStockId() {
		return stockId; }

	@Column(name = "stock_code", nullable = false)	
	public String getStockCode() {
		return stockCode;}

	@Column(name = "stock_name", nullable = false)
	public String getStockName() {
		return stockName;}

	@OneToMany(fetch=FetchType.LAZY,cascade=CascadeType.ALL,mappedBy="stockCategoryExtnId.stock")
	public Set<StockCategoryExtn> getStockCategoryExtn() {
		return stockCategoryExtn;}

	public Stock() { }

	public Stock(String stockCode, String stockName) {
		this.stockCode = stockCode;
		this.stockName = stockName; }

	public String toString() {
		return stockId + " - " + stockCode + " - " + stockName;
	}


	public void setStockId(int stockId) {
		this.stockId = stockId;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public void setStockCategoryExtn(Set<StockCategoryExtn> stockCategoryExtn) {
		this.stockCategoryExtn = stockCategoryExtn;
	}

}
