package com.gs.emp.bo.pojo;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class GenericsStaticData<T> {

	@XmlElement(name="record")
	List<T> tList = null;

	Class<T> tType = null;

	public GenericsStaticData() {
	}

	public GenericsStaticData(Class<T> tType) {
		this.tType = tType;
	}

	public GenericsStaticData(List<T> tList) {
		this.tList = tList;
	}

	public List<T> gettList() {
		return tList;
	}

	public void settList(List<T> tList) {
		this.tList = tList;
	}

}
