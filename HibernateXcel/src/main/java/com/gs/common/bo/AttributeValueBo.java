package com.gs.common.bo;

public class AttributeValueBo {
	public final static long serialVersionUID = 1354367665;

	private Object value;

	private String code;

	public AttributeValueBo() {

	}

	public AttributeValueBo(String value) {
		this.value = value;
	}
	public AttributeValueBo(String code, String value) {
		this.code = code;
		this.value = value;
	}
	public AttributeValueBo(String code, Object object) {
		this.code = code;
		this.value = object;
	}

	/**
	 * @return Returns the value.
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @param value
	 *            The value to set.
	 */
	public void setValue(String value) {
		this.value = value;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
