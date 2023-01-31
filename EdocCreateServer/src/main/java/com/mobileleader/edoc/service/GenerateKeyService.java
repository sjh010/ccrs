package com.mobileleader.edoc.service;

import com.mobileleader.edoc.db.dto.EdocGrpBzwkInfoDto;
import com.mobileleader.edoc.model.response.UploadResponse;

/**
 * 키 생성 서비스 인터페이스
 * 
 * @author yh.kim
 */
public interface GenerateKeyService {

	/**
	 * 키 채번 메인 프로세스
	 */
	public UploadResponse generate(EdocGrpBzwkInfoDto request) throws Exception;
}
