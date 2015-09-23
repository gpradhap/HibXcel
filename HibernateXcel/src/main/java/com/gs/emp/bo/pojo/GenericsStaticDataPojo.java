package com.gs.emp.bo.pojo;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "RecordList")
@XmlAccessorType(XmlAccessType.NONE)
public class GenericsStaticDataPojo<T> {

	Class<T> tType = null;

	@XmlElement(name="record")
	List<T> tList = null;


	public GenericsStaticDataPojo() {
	}

	public GenericsStaticDataPojo(Class<T> tType) {
		this.tType = tType;
	}

	public GenericsStaticDataPojo(List<T> tList) {
		this.tList = tList;
	}

	public List<T> gettList() {
		return tList;
	}

	public void settList(List<T> tList) {
		this.tList = tList;
	}

}
