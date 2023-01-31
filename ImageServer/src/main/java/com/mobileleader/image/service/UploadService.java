package com.mobileleader.image.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.mobileleader.image.server.model.request.UploadRequest;
import com.mobileleader.image.server.model.response.UploadResponse;


/**
 * ECM 파일 업로드 서비스 인터페이스
 * 
 * @author bkcho@mobileleader.com
 * @since 2019.06.24
 *
 */
public interface UploadService {
	
	/*
	 * 업로드 전체 프로세스 구현 함수
	 */
	public UploadResponse upload(UploadRequest req, Map<String, MultipartFile> fileMap) throws Exception;
	
	/*
	 * ECM업로드 전 프로세스
	 */
	public void preProcessing(UploadRequest req);
	
	/*
	 * ECM 업로드 수행
	 */
	public void EcmUploadProcess(UploadRequest req, Map<String, MultipartFile> fileMap) throws Exception;
	
	/*
	 * ECM 업로드 후 DB 저장
	 */
	public void storageDB(UploadRequest req);
	
//	void updateEAI(int resultCode, String resultMsg,  UploadRequest uploadRequest);
//	void scanYn(String mainKey, String scanYn);

}

