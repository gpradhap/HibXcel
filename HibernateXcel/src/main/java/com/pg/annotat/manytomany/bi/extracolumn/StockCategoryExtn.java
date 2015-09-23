package com.pg.annotat.manytomany.bi.extracolumn;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="stock_category_extn")
@AssociationOverrides({
	@AssociationOverride(name="stockCategoryExtnId.stock",joinColumns=@JoinColumn(name="stock_id")),
	@AssociationOverride(name="stockCategoryExtnId.category",joinColumns=@JoinColumn(name="cat_id"))})
public class StockCategoryExtn implements Serializable{

	private StockCategoryExtnId stockCategoryExtnId = new StockCategoryExtnId();
	private String createdBy;
	private Date createdDate;

	private Stock stock;
	private Category category;
	
	//CompositeKey
	@EmbeddedId
	public StockCategoryExtnId getStockCategoryExtnId() {
		return stockCategoryExtnId;}

	@Column
	public String getCreatedBy() {
		return createdBy; }

	@Column
	public Date getCreatedDate() {
		return createdDate;}

	@Transient
	public Stock getStock() {
		return stockCategoryExtnId.getStock();}

	@Transient
	public Category getCategory() {
		return stockCategoryExtnId.getCategory();}
	
	public void setStockCategoryExtnId(StockCategoryExtnId stockCategoryExtnId) {
		this.stockCategoryExtnId = stockCategoryExtnId;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setStock(Stock stock) {
		stockCategoryExtnId.setStock(stock);
	}

	public void setCategory(Category category) {
		stockCategoryExtnId.setCategory(category);
	}
	
	
	public boolean equals(Object object) {
		System.out.println("@@@ StockCategoryExtnId equals checks ");
		
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;
 
		StockCategoryExtn otherObj = (StockCategoryExtn) object;
 
		if (getStockCategoryExtnId() != null ? !getStockCategoryExtnId().equals(otherObj.getStockCategoryExtnId())
				: otherObj.getStockCategoryExtnId() != null){
			System.out.println("@@@ getStockCategoryExtnId not equal ");
			return false;
		}
 
		return true;
	}	
	
}
