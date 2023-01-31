package com.mobileleader.edoc.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mobileleader.edoc.exception.EdocServerStatus;
import com.mobileleader.edoc.util.JsonUtils;

/**
 * 서버 공통 Response 모델
 * 
 * @author yh.kim
 *
 */
public class BaseResponse {
	
	private int statusCode;				//상태 코드
	
	@JsonProperty("corMsg")
	private String resultMessage;		//결과 메세지
	
	@JsonProperty("corCode")
	private String resultCode;			//결과 코드
	
	
	public BaseResponse setResult(EdocServerStatus status) {
		this.statusCode = status.getStatusCode();
		this.resultCode = status.getResultCode();
		this.resultMessage = status.getResultMessage();
		return this;
	}
	
	public int getStatusCode() {
		return statusCode;
	}
	
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String toString() {
		return JsonUtils.ObjectPrettyPrint(this);
	}
}
