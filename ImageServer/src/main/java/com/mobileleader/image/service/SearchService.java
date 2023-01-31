package com.mobileleader.image.service;

import com.mobileleader.image.server.model.request.SearchRequest;
import com.mobileleader.image.server.model.response.SearchResponse;

/**
 * ECM 조회 서비스 인터페이스
 * 
 * @author user
 */
public interface SearchService {
	
	public SearchResponse search(SearchRequest searchReq) throws Exception;
	
}
