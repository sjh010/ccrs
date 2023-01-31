/** ===================================================
 * Copyright (c) 1999-2016 Mobileleader Co., Ltd. All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited 
 * All information contained herein is, and remains the property of Mobileleader Co., Ltd.
 * The intellectual and technical concepts contained herein are proprietary to Mobileleader  Co., Ltd.
 * Dissemination of this information or reproduction of this material is 
 * strictly forbidden unless prior written permission is obtained from Mobileleader Co., Ltd.
 * Proprietary and Confidential.
======================================================= */
package com.mobileleader.image.exception;

public class ServiceException extends RuntimeException {
	static final long serialVersionUID = 0L;
	
	private ServiceError serviceError;

	public ServiceException() {
		super();
	}
	
	public ServiceException(ServiceError error) {
		super(error.getDescription());
		this.serviceError = error;
	}
	
	public ServiceException(ServiceError error, String message, Throwable cause) {
		super(message, cause);
		this.serviceError = error;
	}

	public ServiceException(ServiceError error, String message) {
		super(message);
		this.serviceError = error;
	}

	public ServiceException(ServiceError error, Throwable cause) {
		super(error.getDescription(), cause);
		this.serviceError = error;
	}
	
	public ServiceError getServiceError() {
		return serviceError;
	}
	
	public int getErrorCode() {
		return serviceError.getErrorCode();
	}
		
	public String getDetailMessage() {
		
		String detailMessage = getMessage();
		
		Throwable t = this;
		
		while(t.getCause() != null && t != t.getCause()) {			
			t= t.getCause();			

			detailMessage += "\n";
			detailMessage += t.getMessage();			
		}
		
		return detailMessage;
	}
	
}
