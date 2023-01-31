package com.mobileleader.image.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ecm.api.common.model.EcmApiResponse;

/**
 * ECM 데이터 다운로드 처리 서비스 인터페이스
 * 
 * @since 2018.08.20
 * @author bkcho@mobileleader.com
 *
 */
public interface DownloadService {
 
	/**
	 * ECM API를 통해 서버로 부터 파일을 다운로드 해온다.
	 * 
	 * @param request
	 * @param response
	 * @param fileId
	 * @return 
	 * @throws Exception 
	 */
	public EcmApiResponse getEcmFile(HttpServletRequest request, HttpServletResponse response, String fileId) throws Exception;
	
}
