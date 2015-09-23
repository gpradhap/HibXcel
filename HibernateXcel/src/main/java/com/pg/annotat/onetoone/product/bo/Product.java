package com.pg.annotat.onetoone.product.bo;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity(name="product")
@Table(name="product")
public class Product {

	@Id
	@Column(name="PROD_ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer productId;
	
	@Column(name="PROD_NAME")
	private String productName;

	@Column(name="PROD_DESC")
	private String productDecription;

	//@OneToOne(cascade=CascadeType.ALL)
	//@PrimaryKeyJoinColumn
	@OneToOne(mappedBy="product", cascade=CascadeType.ALL)
	private ProductExtn productExtn;
	
	public Product() {
	}

	public Product(String prodName, String prodDesc) {
		this.productName = prodName;
		this.productDecription = prodDesc;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDecription() {
		return productDecription;
	}

	public void setProductDecription(String productDecription) {
		this.productDecription = productDecription;
	}

	public ProductExtn getProductExtn() {
		return productExtn;
	}

	public void setProductExtn(ProductExtn productExtn) {
		this.productExtn = productExtn;
	}
}
