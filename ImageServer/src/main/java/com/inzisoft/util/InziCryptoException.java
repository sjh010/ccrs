package com.inzisoft.util;

public class InziCryptoException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -605258838899568186L;
	private int errorCode = 0;

	public InziCryptoException(int code) {
		super();
		errorCode = code;
	}

	public InziCryptoException(int code, String message) {

		super(message);
		errorCode = code;
	}

	public int getErrorCode() {
		return this.errorCode;
	}
	
}
