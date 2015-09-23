package com.pg.annotat.onetoone.shareprim.customer.bo;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "customer")
public class Customer implements Serializable {

	@Id
	@Column(name = "cust_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer customerId;

	@Column(name = "cust_name ")
	private String customerName;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="cust_id")
	private CustomerDetails custDtl;
	
	public Customer() {
	}

	public Customer(String custName) {
		this.customerName = custName;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String toString() {
		return getCustomerId() + " : " + getCustomerName() + " : " + (null != getCustDtl()?getCustDtl().getCustomerSSN():"custDtl:null");
	}

	public CustomerDetails getCustDtl() {
		return custDtl;
	}

	public void setCustDtl(CustomerDetails custDtl) {
		this.custDtl = custDtl;
	}
}
