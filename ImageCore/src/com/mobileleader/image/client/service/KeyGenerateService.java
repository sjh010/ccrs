package com.mobileleader.image.client.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mobileleader.image.server.model.request.KeyGenerateRequest;
import com.mobileleader.image.server.model.response.KeyGenerateResponse;
import com.mobileleader.image.util.JsonUtil;
import com.mobileleader.image.util.UserHttpClient;

/**
 * (클라이언트) 키채번 서비스
 * @author user
 *
 */
public class KeyGenerateService {
	
	private static final Logger logger = LoggerFactory.getLogger(KeyGenerateService.class);
	
	private UserHttpClient httpClient = new UserHttpClient();
	
	public KeyGenerateResponse generateKey(String url,  KeyGenerateRequest req) {
		logger.debug("generateKey");
		
		String resultStr = "";
		
		if(req == null) {
			//get
			resultStr = httpClient.messageTrans(url, null);
		} else {
			//post
			resultStr = httpClient.messageTrans(url, req.toString());
		}
		
		return JsonUtil.getObject(resultStr, KeyGenerateResponse.class);
	}

}
