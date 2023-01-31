package com.mobileleader.image.server.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mobileleader.image.util.JsonUtil;

/**
 * 키 생성 Request 모델
 * 
 * @author user
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class KeyGenerateRequest {
	
	public String toString() {
		return JsonUtil.ObjectPrettyPrint(this);
	}

}
