package com.mobileleader.image.service;

import com.mobileleader.image.server.model.response.KeyGenerateResponse;

/**
 * 전자문서 통합키(MainKey) 채번 서비스 인터페이스
 */
public interface KeyGenerateService {
	
	public KeyGenerateResponse generateKey() throws Exception;


}
