package com.mobileleader.image.server.model.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 공통 응답에 대한 성공여부 응답
 * 
 * @author bkcho@mobileleader.com
 * @since 2018.08.23
 */
@JsonAutoDetect
@JsonInclude(Include.NON_NULL)
public class BaseResponse {
	
	private int resultCode;						// 결과 코드
	private String resultMessage;				// 결과 메세지
	
//	public BaseResponse() {
//		setResultCode(0);
//		setResultMessage("known error");		
//	}
//
//	public BaseResponse(int resultCode, String resultMessage) {
//		super();
//		this.resultCode = resultCode;
//		this.resultMessage = resultMessage;
//	}
	
	public int getResultCode() {
		return resultCode;
	}
	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultMessage() {
		return resultMessage;
	}
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

}

