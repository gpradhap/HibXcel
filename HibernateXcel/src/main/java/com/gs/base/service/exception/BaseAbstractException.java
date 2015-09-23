package com.gs.base.service.exception;

import org.apache.commons.lang.StringUtils;

public abstract class BaseAbstractException extends Exception{

	private String errorMsg = null;
	private String errorCode = null;
	private String rootException = null;
	
	public BaseAbstractException() {
	}

	public BaseAbstractException(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public BaseAbstractException(String errorCode, String errorMsg) {
		this.errorCode = errorCode; 
		this.errorMsg = errorMsg;
	}

	public BaseAbstractException(String errorCode, String errorMsg, String rootException) {
		this.errorCode = errorCode; 
		this.errorMsg = errorMsg;
		this.rootException = rootException;
	}
	
	@Override
	public String getMessage() {
		String exceptionMsg = null;
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append(null != getErrorCode() ? getErrorCode()+" : " : "");
		//strBuilder.append(" : ");
		strBuilder.append(null != getErrorMsg() ? getErrorMsg()+" - " : "");
		//strBuilder.append(" - ");
		strBuilder.append(null != getRootException() ? getRootException() : "");
		
		exceptionMsg = strBuilder.toString();
		
		if(StringUtils.isBlank(exceptionMsg)){
			exceptionMsg = super.getMessage();
		}
		return exceptionMsg;
	}
	
	public String getErrorMsg(){
		return this.errorMsg; 
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getRootException() {
		return rootException;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public void setRootException(String rootException) {
		this.rootException = rootException;
	}
}
