package com.pg.annotat.manytomany.bi.extracolumn;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class StockCategoryExtnId  implements Serializable{
	private Stock stock;
	private Category category;

	@ManyToOne(cascade = CascadeType.ALL)
	public Stock getStock() {
		return stock; }
	
	@ManyToOne(cascade = CascadeType.ALL)
	public Category getCategory() {
		return category; }

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
 
        StockCategoryExtnId that = (StockCategoryExtnId) o;
 
        if (stock != null ? !stock.equals(that.stock) : that.stock != null){
        	System.out.println("@@@ STOCK not equal ");
        	return false;
        }
        if (category != null ? !category.equals(that.category) : that.category != null){
        	System.out.println("@@@ Category not equal ");
        	return false;
        }
 
        return true;
    }
}
