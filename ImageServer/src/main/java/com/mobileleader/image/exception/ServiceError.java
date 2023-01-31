//=======================================================
/* Copyright (c) 1999-2016 Mobileleader Co., Ltd. All Rights Reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited 
 * All information contained herein is, and remains the property of Mobileleader Co., Ltd.
 * The intellectual and technical concepts contained herein are proprietary to Mobileleader  Co., Ltd.
 * Dissemination of this information or reproduction of this material is 
 * strictly forbidden unless prior written permission is obtained from Mobileleader Co., Ltd.
 * Proprietary and Confidential.
 */
//=======================================================

/**
 *
 */
package com.mobileleader.image.exception;

/**
 * 서비스 에러 코드
 * (신용회복위원회 프로젝트에서 사용하지  않은 코드 주석처리 함)
 * 
 * @version 1.0
 * @author ares09@mobileleader.com
 * @since 2016. 5. 7.
 * 
 */
public enum ServiceError {
	
	//--- 4xxxx Client Error ---
	
	BAD_REQUEST(400, 40000, "Bad Request"),
	
	INVALID_REQUEST_PARAMETER(400, 40001, "Invalid request parameter. Check parameter validation condition."),
	
	//UNEXPECTED_CONTENT(400, 40002, "Unexpected contents."),
	
	//PAGE_OUT_OF_RANGE(400, 40003, "Request page is out of range. Check max pageCount."),
	
	//INVALID_URL_PARAMETER(400, 40004, "Invalid URL path parameter. Check parameter validation condition."),
	
	UNAUTHORIZED(401, 40100, "Unauthorized"),
	
	//PAYMENT_REQUIRED(402, 40200, "Payment Required"),
	
	//FORBIDDEN(403, 40300, "Forbidden"),
	
	NOT_FOUND(404, 40400, "Not Found"),
	
	NOT_FOUND_FILE(404, 40401, "File Not Found"),

	//NOT_FOUND_ANNOTATION(404, 40402, "Annotaion Not Found"),
	
	NOT_FOUND_FORM(404, 40403, "Form Not Found."),
	
	//METHOD_NOT_ALLOWED(405, 40500, "Method Not Allowed"),
	
	//NOT_ACCEPTABLE(406, 40600, "Not Acceptable"),
	
	//PROXY_AUTHENTICATION_REQUIRED(407, 40700, "Proxy Authentication Required"),
	
	//REQUEST_TIMEOUT(408, 40800, "Request Timeout"),
	
	//CONFLICT(409, 40900, "Conflict"),
	
	//GONE(410, 41000, "Gone"),
	
	//LENGTH_REQUIRED(411, 41100, "Length Required"),
	
	//PRECONDITION_FAILED(412, 41200, "Precondition Failed"),
	
	//PAYLOAD_TOO_LARGE(413, 41300, "Payload Too Large"),
	
	//URI_TOO_LONG(414, 41400, "URI Too Long"),
	
	UNSUPPORTED_MEDIA_TYPE(415, 41500, "Unsupported Media Type"),
	
	//REQUESTED_RANGE_NOT_SATISFIABLE(416, 41600, "Requested range not satisfiable"),
	
	//EXPECTATION_FAILED(417, 41700, "Expectation Failed"),
	
	
	
	// --- 5xxxx Server Error ---
	INTERNAL_SERVER_ERROR(500, 50000, "Internal Server Error"),
	
	//INVALID_METHOD_PARAMETER(500, 50001, "Invalid method parameter."),
	
	FILE_NOT_EXIST(500, 50002, "File not exist."),
	
	//FILE_READ_FAIL(500, 50003, "File exist but read fail."),
	
	FILE_IO_ERROR(500, 50022, "File I/O Exception"),
	
	FAIL_CONVERT_FORMAT(500, 50004, "Fail convert format."),
	
	UNSUPPORTED_TARGET_FILE_FORAMT(500, 50005, "Unsupported target file format."),
	
	UNSUPPORTED_TARGET_COMPRESS_FORAMT(500, 50006, "Unsupported target compress format."),
	
	//FAIL_GET_TIF_BINARY(500, 50007, "Could't get tif file binary."),
	
	//FAIL_GET_PDF_BINARY(500, 50008, "Could't get pdf file binary."),
	
	//DDIMS_ERROR(500, 50009, "Ddims error."),
	
	//CACHE_LOADING_ERROR(500, 50010, "Cache loading error."),
	
	//CACHE_DELETE_ERROR(500, 50011, "Cache delete error."),
	
	//PDF_CONVERT_ERROR(500, 50012, "Error ocured when convert to pdf."),	
	
	//PDF_EDITOR_ERROR(500, 50012, "Error ocured from pdf editor."),
	
	//PAGE_EXTRACT_ERROR(500, 50013, "Error ocured when extract page."),
	
	//STORAGE_NOT_SUPPORT_ANNOTATION(500, 50014, "File storage not support annotation."),
	
	//TSA_SIGN_ERROR(500, 50017, "PDF tsa sign error."),
	
	//MALFORMED_ANNOTATION(500, 50018, "Malformed annotation format, check annotation format."),
	
	//SOCKET_CONNECTION_ERROR(500, 50019, "Socket connnection error."),
	
	//SOCKET_IO_ERROR(500, 50020, "Socket io error."),
	
	ENCODING_ERROR(500, 50021, "Encoding Error. UnSupported Encoding Type"),
	
	//NOT_IMPLEMENTED(501, 50100, "Not Implemented"),
	
	//BAD_GATEWAY(502, 50200, "Bad Gateway"),
	
	//SERVICE_UNAVAILABLE(503, 50300, "Service Unavailable"),
	

	// --- 8xxxx ---
	DATABASE_ACCESS_ERROR(800, 80000, "DB Access Error."),
	
	SQL_ERROR(800, 80001, "Sql Error"),
	
	CRYPTO_ERROR(801, 80100, "Inzi Crypto API Error"),
	
	FILE_ENCRYPT_ERROR(801, 80101, "File encrypt error."),
	
	FILE_DECRYPT_ERROR(801, 80102, "File decrypt error."),
	
	ECM_ERROR(802, 80200, "ECM Server Error"),
	
	ECM_CONNECT_ERROR(802, 80201, "ECM Connect Failed"),
	
	ECM_UPLOAD_FAILED(802, 80202, "ECM Upload Failed"),
	
	ECM_DOWNLOAD_FAILED(802, 80203, "ECM Download Failed"),
	
	
	// --- 9xxxx ---
	DATA_NOT_EXIST(900, 90000, "Requested Data Does Not Exist")
	;
	

    private final int status;

	private final Integer errorCode;
	
    private final String description;

    private ServiceError(int status, int errorCode, String description) {
        this.status = status;
        this.errorCode = errorCode;
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public Integer getErrorCode() {
    	return errorCode;
    }
    
    public String getDescription() {
        return description;
    }
    
    public static ServiceError fromErrorCode(int errorCode) {
    	for(ServiceError error : values()) {
    		if(error.errorCode == errorCode)
    			return error;
    	}
    	
    	return null;
    }

}
