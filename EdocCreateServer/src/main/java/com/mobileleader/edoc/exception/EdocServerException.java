package com.mobileleader.edoc.exception;

public class EdocServerException extends RuntimeException {

	private static final long serialVersionUID = 0L;
	
	private EdocServerStatus error;
	
	public EdocServerException() {
		super();
	}
	public EdocServerException(String message) {
		super(message);
	}
	public EdocServerException(Throwable cause) {
		super(cause);
	}
	public EdocServerException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public EdocServerException(EdocServerStatus error) {
		super(error.getResultMessage());
		this.error = error;
	}
	public EdocServerException(EdocServerStatus error, String message) {
		super(message);
		this.error = error;
	}
	public EdocServerException(EdocServerStatus error, Throwable cause) {
		super(error.getResultMessage(), cause);
		this.error = error;
	}
	public EdocServerException(EdocServerStatus error, String message, Throwable cause) {
		super(message, cause);
		this.error = error;
	}
	
	public EdocServerStatus getError() {
		return error;
	}
	public void setError(EdocServerStatus error) {
		this.error = error;
	}
	
	public int getStatusCode() {
		return error.getStatusCode();
	}
	public String getErrorCode() {
		return error.getResultCode();
	}
	public String getErrorMessage() {
		return error.getResultMessage();
	}
	
	public String getDetailMessage() {
		
		Throwable t = this;
		StringBuilder detailMsg = new StringBuilder(getMessage());
		
		while(t.getCause() != null && t != t.getCause()) {
			t = t.getCause();
			
			detailMsg.append("\n" + t.getMessage());
		}
		
		return detailMsg.toString();
	}

}
