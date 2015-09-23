package com.pg.annotat.onetoone.product.bo;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "product_extn")
public class ProductExtn {

	@Id
	@Column(name = "PROD_EXTN_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer prodExtnId;

	@Column(name = "PROD_CATEGORY")
	private String prodCategory;

	@Column(name = "PROD_CREATED_DATE")
	private Date prodCreateDate;

	@GenericGenerator(name = "gen", strategy = "foreign", parameters = @Parameter(name = "property", value = "product"))
	@GeneratedValue(generator = "gen")
	@Column(name = "PROD_ID", nullable = false)
	private Integer prodId;

	//@OneToOne(mappedBy="productExtn", cascade=CascadeType.ALL)
	@OneToOne
	@PrimaryKeyJoinColumn
	//@JoinColumn(name="prod_id")
	private Product product;

	public ProductExtn() {
	}

	public ProductExtn(String prodCat, Date createDate) {
		this.prodCategory = prodCat;
		this.prodCreateDate = createDate;
	}

	public ProductExtn(String prodCat, Date createDate, Integer prodIdFK) {
		this.prodCategory = prodCat;
		this.prodCreateDate = createDate;
		this.prodId = prodIdFK;
	}

	public Integer getProdExtnId() {
		return prodExtnId;
	}

	public void setProdExtnId(Integer prodExtnId) {
		this.prodExtnId = prodExtnId;
	}

	public String getProdCategory() {
		return prodCategory;
	}

	public void setProdCategory(String prodCategory) {
		this.prodCategory = prodCategory;
	}

	public Date getProdCreateDate() {
		return prodCreateDate;
	}

	public void setProdCreateDate(Date prodCreateDate) {
		this.prodCreateDate = prodCreateDate;
	}

	public Integer getProdId() {
		return prodId;
	}

	public void setProdId(Integer prodId) {
		this.prodId = prodId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
