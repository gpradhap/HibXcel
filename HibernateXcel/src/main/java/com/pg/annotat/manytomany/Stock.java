package com.pg.annotat.manytomany;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.gs.base.bo.BaseAbstractBo;

@Entity
@Table(name = "stock")
public class Stock  extends BaseAbstractBo implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "stock_id")
	private int stockId;

	@Column(name = "stock_code", nullable = false)
	private String stockCode;

	@Column(name = "stock_name", nullable = false)
	private String stockName;
	
	@ManyToMany(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	@JoinTable(name="stock_category",catalog="gsportaldb",joinColumns={@JoinColumn(name="stock_id")},inverseJoinColumns={@JoinColumn(name="cat_id")})
	private List<Category> categories;

	public Stock() { }

	public Stock(String stockCode, String stockName) {
		this.stockCode = stockCode;
		this.stockName = stockName; }

	public String toString() {
		return stockId + " - " + stockCode + " - " + stockName;
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

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

}
