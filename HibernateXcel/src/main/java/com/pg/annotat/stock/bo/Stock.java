package com.pg.annotat.stock.bo;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

@Entity
@Table(name = "stock", catalog = "hibdb")
public class Stock implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "STOCK_ID")
	private int stockId;

	@Column(name = "STOCK_CODE", nullable = false)
	private String stockCode;

	@Column(name = "STOCK_NAME", nullable = false)
	private String stockName;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "stock")
	@Cascade({org.hibernate.annotations.CascadeType.ALL})
	private Set<StockDailyRecord> stockDailyRecord;

	public Stock() { }

	public Stock(String stockCode, String stockName) {
		this.stockCode = stockCode;
		this.stockName = stockName; }

	public Stock(String stockCode, String stockName, Set<StockDailyRecord> stockDailyRecord) {
		this.stockCode = stockCode;
		this.stockName = stockName;
		this.stockDailyRecord = stockDailyRecord; }
	
	public String toString() {
		return stockId + " - " + stockCode + " - " + stockName;
	}

	public Set<StockDailyRecord> getStockDailyRecord() {
		return stockDailyRecord;
	}

	public void setStockDailyRecord(Set<StockDailyRecord> stockDailyRecord) {
		this.stockDailyRecord = stockDailyRecord;
	}

	public int getStockId() {
		return stockId;
	}

	public void setStockId(int stockId) {
		this.stockId = stockId;
	}

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
}
