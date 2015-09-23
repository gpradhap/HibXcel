package com.pg.annotat.onetoone.uni.contact.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

@Entity
@Table(name = "passport")
public class Passport implements Serializable {

	private static final long serialVersionUID = 5262606338110831970L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "passport_id")
	private Integer passportId;

	@Column(name = "passport_no")
	private String passportNumber;

	//@OneToOne(mappedBy="pass",cascade = CascadeType.ALL)
	//@OneToOne(mappedBy="pass")
	@OneToOne
	@JoinColumn(name = "passport_id",updatable=true,nullable=true) //for bi-direction either JoinColumn or mappedBy
	@Cascade({org.hibernate.annotations.CascadeType.ALL,org.hibernate.annotations.CascadeType.DELETE })
	private Contact contact;

	public Passport() {
	}

	public Passport(String passportNo) {
		this.passportNumber = passportNo;
	}

	public Integer getPassportId() {
		return passportId;
	}

	public void setPassportId(Integer passportId) {
		this.passportId = passportId;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public String toString() {
		return (null != getContact() ? getContact().getContactId()
				: "ContactId:null")
				+ " : "
				+ (null != getContact() ? getContact().getContactType()
						: "ContactType:null")
				+ " : Pass : "
				+ getPassportNumber();
	}

}
