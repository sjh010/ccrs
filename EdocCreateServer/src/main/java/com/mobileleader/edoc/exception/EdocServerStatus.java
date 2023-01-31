package com.mobileleader.edoc.exception;

/**
 * Response Code
 * 
 * @author yh.kim
 *
 */
public enum EdocServerStatus {
	
	/*
	 * errorCode = E(EdocServer Process Id) + Number(2) + Number(3)
	 * 
	 * Number(2) : Service Interface 분류 코드
	 * 	00 : Service 공통
	 * 	10 : GenerateEdocKeyService
	 * 	20 : UploadEdocDataService
	 * 	30 : UploadTempDataService
	 * 	40 : SearchTempDataService
	 * 	50 : SearchTempListService
	 * 	60 : EformDownloadService
	 * 
	 * Number(3) : exception 분류 코드
	 */
	
	OK(200, "OK", "SUCCESS"),

	/*
	 * 4XX : client Error
	 */
	BAD_REQUEST(400, "E00400", "Bad Request"),
	
	/* 
	 * 5xx : internal Server error
	 */
	INTERNAL_SERVER_ERROR(500, "E00500", "Unknown Internal Server Error"),
	
	UNKNOWN_HOST_ERROR(500, "E00501", "Unknown Host Exception"),

	FILE_READ_ERROR(510, "E00511", "IOException - File Input Error"),
	
	FILE_WRITE_ERROR(510, "E00512", "IOException - File Output Error"),
	
	FILE_TRANSFER_ERROR(510,"E00513", "MultipartFile Transfer Failed"),
	
	FILE_PARSING_ERROR(510, "E00514", "File Parsing Failed"),
	
	FILE_CONVERT_ERROR(510, "E00515", "File Convert Error"),
	
	FILE_DECOMPRESS_ERROR(510, "E00516", "Zip File Decompress Failed"),
	
	UNSUPPORTED_ENCODING_TYPE(520, "E00521", "Unsupported Encoding Type"),
	
	UNSUPPORTED_MEDIA_TYPE(520, "E00522", "Unsupported Media Type"),
	
	UNSUPPORTED_MIME_TYPE(520, "E00523", "Unsupported Mime Type"),
	
	UNSUPPORTED_TARGET_FILE_FORAMT(520, "E00524", "Unsupported target file format."),
	
	UNSUPPORTED_TARGET_COMPRESS_FORAMT(520, "E00525", "Unsupported target compress format."),
	
	
	/* 
	 * 8xx : handled service error
	 */
	//800 : invalid request
	INVALID_REQUEST(800, "E00800", "Invalid Request. Check parameter validation condition."),
	
	INVALID_EDOC_KEY(800, "E00801", "Invalid EdocGrpIdxNo."),
	
	INVALID_CFG_FILE(800,"E10802", "Result Data.Cfg File Validation Failed"),
	
	INVALID_GUID_FORMCODE_LINK(800, "E10803", "GUID_FORMCODE_LINK Info Validation Failed"),
	
	INVALID_XML_FILE(800, "E10804", "RESULT XML File VALIDATION FAIL"),
	
	XML_FILE_NOT_FOUND(800, "E10805", "Result XML FILE not EXIST"),

	//810 : Request Data/File Not Found
	NO_TMP_DATA(810, "E40810", "TmpStrgMgmt Data is Not Exist"),
	
	NO_TMP_LIST(810, "E50810", "TmpStrgMgmt List is Not Exist"),
	
	TMP_FILE_NOT_EXIST(810, "E40811", "TmpDataFile is Not Exist"),
	
	FORM_FILE_NOT_EXIST(810, "E60811", "FormFile is Not Exist"),
	
	//820 : already processed edocGroup
	REQUEST_ALREADY_PROCESSING(820, "E10820", "Requested EdocGrpIdxNo Already Processing"),
	
	REQUEST_FILE_ALREADY_PROCESSED(820, "E40820", "Requested Temp File Already Processed"),

	//890 : Access denied
	NO_AUTHORITY(890, "E00890", "No Authority"),


	/* 
	 * 9XX - External service/library error
	 */
	//900 : DB
	DB_CONNECT_ERROR(900, "E00900", "DataBase Connect Failed"),
	
	SQL_ERROR(900, "E00901", "SQL Error"),
	
	DB_INSERT_ERROR(900, "E00902", "Data Insert ERROR"),
	
	DB_UPDATE_ERROR(900, "E00903", "Data Update ERROR"),
	
	DB_DELETE_ERROR(900, "E00904", "Data Delete ERROR"),
	
	DB_SELECT_ERROR(900, "E00905", "Data Select Error"),
	
	
	//910 : crypto
	ENCRYPT_ERROR(910, "E00911", "Encrypt Error"),
	
	DECRYPT_ERROR(910, "E00912", "Decrypt Error"),
	
	FILE_ENCRYPT_ERROR(910, "E00913", "File Encrypt Error"),
	
	FILE_DECRYPT_ERROR(910, "E00914", "File Decrypt Error."),
	
	DATA_ENCRYPT_ERROR(910, "E00915", "Data Encrypt Error"),
	
	DATA_DECRYPT_ERROR(910, "E00916", "Data Decrypt Error")

	;
	
	
    private final int statusCode;

	private final String resultCode;
	
    private final String resultMessage;

    private EdocServerStatus(int statusCode, String resultCode, String resultMessage) {
        this.statusCode = statusCode;
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }

    public int getStatusCode() {
        return statusCode;
    }
    
    public String getResultCode() {
		return resultCode;
	}

	public String getResultMessage() {
		return resultMessage;
	}

}
