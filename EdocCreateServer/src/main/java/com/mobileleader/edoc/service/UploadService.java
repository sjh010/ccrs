package com.mobileleader.edoc.service;

import org.springframework.web.multipart.MultipartFile;

import com.mobileleader.edoc.db.dto.EdocGrpBzwkInfoDto;
import com.mobileleader.edoc.model.response.UploadResponse;

/**
 * 업로드된 파일 처리 서비스 인터페이스
 * 
 * @author yh.kim
 */
public interface UploadService {
	
	/*
	 * 파일 저장 메인 프로세스
	 */
	public UploadResponse upload(EdocGrpBzwkInfoDto request, MultipartFile file) throws Exception;

}
