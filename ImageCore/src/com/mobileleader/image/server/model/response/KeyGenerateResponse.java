package com.mobileleader.image.server.model.response;

import com.mobileleader.image.util.JsonUtil;

/**
 * 키 채번 요청에 대한 응답 모델
 * @author user
 *
 */
public class KeyGenerateResponse extends BaseResponse{
	
	private String mainKey;								// 통합 전자문서키(mainKey)

	public String getMainKey() {
		return mainKey;
	}

	public void setMainKey(String mainKey) {
		this.mainKey = mainKey;
	}
	
	public String toString() {
		return JsonUtil.ObjectPrettyPrint(this);
	}
}
