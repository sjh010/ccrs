package com.mobileleader.image.client.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mobileleader.image.client.Exception.UserHttpStatus;
import com.mobileleader.image.server.model.request.SearchRequest;
import com.mobileleader.image.server.model.response.SearchListResponse;
import com.mobileleader.image.server.model.response.SearchResponse;
import com.mobileleader.image.server.model.response.SearchStoreListResponse;
import com.mobileleader.image.util.JsonUtil;
import com.mobileleader.image.util.UserHttpClient;

/**
 * (클라이언트) 이미지 검색 서비스
 * 
 * @author bkcho@mobileleader.com
 * @since 2018.08.23
 *
 */
public class SearchService {
	private static final Logger logger = LoggerFactory.getLogger(SearchService.class);
		
	private UserHttpClient userHttpClient = new UserHttpClient();

	public SearchResponse searchByMainkeyString(String url, String json) {
		logger.debug("in searchByMainkey");
		
		String jsonRes = userHttpClient.messageTrans(url, json);
		SearchResponse res  = JsonUtil.getObject(jsonRes, SearchResponse.class);		
		if (res == null) {
			res = new SearchResponse();
			res.setResultCode(UserHttpStatus.CLIENT_SERVICE_ERROR.getStatusCode());
			res.setResultMessage("unknow error");
		}
		
		return res;
	}
	
	public SearchResponse searchByMainkey(String url, SearchRequest req) {
		return searchByMainkeyString(url, req.toString());
	}
	 
	public SearchListResponse searchByMainkeyList(String url, String json) {
		logger.debug("in searchByMainkeyList");
		
		String jsonRes = userHttpClient.messageTrans(url, json);		
		SearchListResponse res  = JsonUtil.getObject(jsonRes, SearchListResponse.class);
		if (res == null) {
			res = new SearchListResponse();
			res.setResultCode(UserHttpStatus.CLIENT_SERVICE_ERROR.getStatusCode());
			res.setResultMessage("unknow error");
		}
		
		return res;
	}
	
	public SearchStoreListResponse searchBySearchForm(String url, String json) {
		logger.debug("in searchBySearchForm");
		
		String jsonRes = userHttpClient.messageTrans(url, json);
		
		SearchStoreListResponse res = JsonUtil.getObject(jsonRes, SearchStoreListResponse.class);
		
		if (res == null) {
			res = new SearchStoreListResponse();
			res.setResultCode(404);
			res.setResultMessage("Result is Null");
		}
		
		return res;
	}
	
}
