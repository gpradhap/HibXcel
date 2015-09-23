package com.pg.annotat.manytomany;

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

@Entity(name="category")
@Table(name="category")
public class Category extends BaseAbstractBo{

	@Id
	@Column(name="cat_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int categoryId;

	@Column(name="cat_name")
	private String categoryName;

	@Column(name="cat_description")
	private String categoryDesc;
	
	@ManyToMany(fetch=FetchType.LAZY,mappedBy="categories")
	private List<Stock> stocks;

	public Category() {	}
	
	public Category(String categoryName, String categoryDesc) {
		this.categoryName = categoryName;
		this.categoryDesc = categoryDesc;
	}
	
	public int getCategoryId() {
		return categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public String getCategoryDesc() {
		return categoryDesc;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public void setCategoryDesc(String categoryDesc) {
		this.categoryDesc = categoryDesc;
	}

	public List<Stock> getStocks() {
		return stocks;
	}

	public void setStocks(List<Stock> stocks) {
		this.stocks = stocks;
	}

}
