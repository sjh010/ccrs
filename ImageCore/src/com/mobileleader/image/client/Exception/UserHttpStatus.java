package com.mobileleader.image.client.Exception;

/**
 * 클라이언트 에러코드 정의 클래스
 *
 */
public enum UserHttpStatus {
	
	// --- 4xx Client Error ---
	OK(200, "OK"),
	
	CLIENT_SERVICE_ERROR(444, "Client Service Error"),
	
	BAD_REQUEST(400, "Bad Request"),
	
	UNAUTHORIZED(401, "UnAuthorized"),
	
	PAYMENT_REQUIRED(402, "Payment Required"),
	
	FORBIDDEN(403, "Forbidden"),
	
	NOT_FOUND(404, "Not Found"),
	
	METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
	
	NOT_ACCEPTABLE(406, "Not Acceptable"),
	
	PROXY_AUTHENTICATION_REQUIRED(407, "Authentication Required"),

	REQUEST_TIMEOUT(408, "Request TimeOut"),

	CONFLICT(409, "Conflict"),
	
	GONE(410, "Gone"),
	
	LENGTH_REQUIRED(411, "Length Required"),
	
	PRECONDITION_FAILED(412, "PreCondition Failed"),
	
	REQUEST_TOO_LONG(413, "Request Too Long"),
	
	REQUEST_URI_TOO_LONG(414, "Request URI Too Long"),
	
	UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"),
	
	REQUESTED_RANGE_NOT_SATISFIABLE(416, "Requested Range Not Satisfiable"),
	
	EXPECTATION_FAILED(417, "Expectation Failed"),
	
	INSUFFICIENT_SPACE_ON_RESOURCE(419, "Insufficient Space On Resource"),
	
	METHOD_FAILURE(420, "Method Failure"),
	
	UNPROCESSABLE_ENTITY(422, "Unprocessable Entity"),
	
	LOCKED(423, "Locked"),
	
	FAILED_DEPENDENCY(424, "Failed Dependency"),
	
	
	// --- 5xx Server Error ---

	NOT_IMPLEMENTED(501, "Not Implemented"),
	
	BAD_GATEWAY(502, "Bad Gateway"),
	
	SERVICE_UNAVAILABLE(503, "Service Unavailable"),
	
	GATEWAY_TIMEOUT(504, "Gateway Timeout"),
	
	HTTP_VERSION_NOT_SUPPORTED(505, "HTTP Version Not Supported"),
	
	INSUFFICIENT_STORAGE(507, "Insufficient Storage")
	;

	private int statusCode;
	private String statusMessage;
	
	private UserHttpStatus(int statusCode, String statusMessage) {
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public static UserHttpStatus getByCode(int statusCode) {
		for(UserHttpStatus type : UserHttpStatus.values()) {
			if(type.getStatusCode() == statusCode) {
				return type;
			}
		}
		return null;
	}

}
