package com.gs.doc.service.exception;

import com.gs.base.service.exception.BaseAbstractException;

public class DocumentServiceException extends BaseAbstractException{
	
	public DocumentServiceException() {
	}
	public DocumentServiceException(String errorMsg) {
		super(errorMsg);
	}

	public DocumentServiceException(String errorCode, String errorMsg) {
		super(errorCode,errorMsg);
	}

	public DocumentServiceException(String errorCode, String errorMsg, String rootException) {
		super(errorCode,errorMsg,rootException);
	}
}
