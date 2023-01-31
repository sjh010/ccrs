package com.mobileleader.edoc.service;

import com.mobileleader.edoc.db.dto.EdocGrpBzwkInfoDto;
import com.mobileleader.edoc.model.response.SearchResponse;

/**
 * 조회 서비스 인터페이스
 * 
 * @author yh.kim
 */
public interface SearchService {

	/*
	 * DB데이터 조회
	 */
	public SearchResponse search(EdocGrpBzwkInfoDto request) throws Exception;
	
	/*
	 * 서버 저장 파일경로 조회
	 */
	public String searchSvrFilePath(EdocGrpBzwkInfoDto request) throws Exception;

}
