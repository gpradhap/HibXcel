package com.pg.annotat.onetoone.uni.contact.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "contact")
public class Contact implements Serializable {

	private static final long serialVersionUID = 5262606338110831970L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "contact_id")
	private Integer contactId;

	@Column(name = "contact_type")
	private String contactType;

	// @OneToOne(mappedBy="contact",cascade = CascadeType.ALL, fetch =
	// FetchType.EAGER) //For Bi-direction
	@OneToOne(fetch = FetchType.EAGER,mappedBy="contact",orphanRemoval=true)
	// -- For UniDirectional
	@Cascade({CascadeType.ALL})
	//@JoinColumn(name = "pass_id", insertable = true, updatable = true, nullable = true)
	private Passport pass;

	public Contact() {
	}

	public Contact(String contactType) {
		this.contactType = contactType;
	}

	public Integer getContactId() {
		return contactId;
	}

	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}

	public String getContactType() {
		return contactType;
	}

	public void setContactType(String contactType) {
		this.contactType = contactType;
	}

	public Passport getPass() {
		return pass;
	}

	public void setPass(Passport pass) {
		this.pass = pass;
	}

	public String toString() {
		return getContactId() + " : " + getContactType() + " : Pass : "
				+ (null != getPass() ? getPass().getPassportNumber() : "Null");
	}
}
