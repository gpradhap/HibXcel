package com.pg.annotat.onetoMany.depart.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "DEPART_STORE")
public class DepartStore {

	@Id
	@Column(name = "STORE_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer storeId;

	@Column(name = "STORE_ADDRESS")
	private String storeAddress;

	@Column(name = "STORE_NAME")
	private String storeName;

	@ManyToOne
	@JoinColumn(name = "depart_id")
	@Cascade({CascadeType.ALL})
	private Department department;

	public DepartStore() {
	}

	public DepartStore(String storeName, String storeAddress) {
		this.storeName = storeName;
		this.storeAddress = storeAddress;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	public String getStoreAddress() {
		return storeAddress;
	}

	public void setStoreAddress(String storeAddress) {
		this.storeAddress = storeAddress;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

}
