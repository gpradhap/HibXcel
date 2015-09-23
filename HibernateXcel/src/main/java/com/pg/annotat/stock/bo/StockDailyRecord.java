package com.pg.annotat.stock.bo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="stock_daily_record")
public class StockDailyRecord {

	private Integer recordId;
	private Stock stock;
	private Float priceOpen;
	private Float priceClose;
	private Date txnDate;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="DAILY_RECORD_ID")
	public Integer getRecordId() {
		return recordId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STOCK_ID", nullable = false)
	public Stock getStock() {
		return stock;
	}

	@Column(name="PRICE_OPEN")
	public Float getPriceOpen() {
		return priceOpen;
	}

	@Column(name="PRICE_CLOSE")
	public Float getPriceClose() {
		return priceClose;
	}

	@Column(name="TXN_DATE")
	public Date getTxnDate() {
		return txnDate;
	}

	public StockDailyRecord() {
	}

	public StockDailyRecord(Stock stock, Float priceOpen, Float priceClose, Date txnDate) {
		this.stock = stock;
		this.priceOpen = priceOpen;
		this.priceClose = priceClose;
		this.txnDate = txnDate;
	}
	

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public void setPriceOpen(Float priceOpen) {
		this.priceOpen = priceOpen;
	}

	public void setPriceClose(Float priceClose) {
		this.priceClose = priceClose;
	}
	public void setTxnDate(Date txnDate) {
		this.txnDate = txnDate;
	}
	
	
}

