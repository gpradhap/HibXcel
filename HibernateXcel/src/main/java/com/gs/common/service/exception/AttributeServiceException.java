package com.gs.common.service.exception;

import com.gs.base.service.exception.BaseAbstractException;

public class AttributeServiceException extends BaseAbstractException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5256338651614197318L;

	public AttributeServiceException() {
	}
	public AttributeServiceException(String errorMsg) {
		super(errorMsg);
	}

	public AttributeServiceException(String errorCode, String errorMsg) {
		super(errorCode,errorMsg);
	}

	public AttributeServiceException(String errorCode, String errorMsg, String rootException) {
		super(errorCode,errorMsg,rootException);
	}
}
