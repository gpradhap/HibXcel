package com.pg.annotat.onetoone.shareprim.customer.bo;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity(name="CustomerDetails")
@Table(name = "customer_dtl")
public class CustomerDetails implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cust_id")
	private Integer customerId;

	@Column(name = "cust_ssn")
	private String customerSSN;

	@OneToOne(mappedBy="custDtl",cascade=CascadeType.ALL)
	private Customer customer;
	
	public CustomerDetails() {
	}

	public CustomerDetails(String ssn) {
		this.customerSSN = ssn;
	}

	public String toString() {
		return getCustomerId() + " : " + getCustomerSSN()+" : "+(null!=getCustomer()?getCustomer().getCustomerName():"cust:null");
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getCustomerSSN() {
		return customerSSN;
	}

	public void setCustomerSSN(String customerSSN) {
		this.customerSSN = customerSSN;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
