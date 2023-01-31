package com.mobileleader.image.service;

import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecm.api.DownloadListener;
import com.ecm.api.EcmApi;
import com.ecm.api.ProfileList;
import com.ecm.api.common.exception.EcmApiException;
import com.ecm.api.common.model.EcmApiResponse;
import com.mobileleader.image.properties.EcmProperties;

/**
 * ECM 파일 업로드/다운로드 서비스 
 * 
 * @since 2018.08.20
 * @author bkcho@mobileleader.com
 *
 */
@Service
public class EcmService {
	
	private static final Logger logger = LoggerFactory.getLogger(EcmService.class);
	
	@Autowired(required=true)
	protected EcmProperties ecmProperty;
	
	/**
	 * ECM 서버에 연결한다.
	 */
	private EcmApi connect() {
		logger.debug(ecmProperty.toString());
		
		EcmApi ecmApi = EcmApi.connect(ecmProperty.getServerIp(), ecmProperty.getServerPort(), ecmProperty.getServerId(), ecmProperty.getServerPw());
			
		if (ecmApi.isConnected()){
			logger.debug("ecm server connected.");
		}
		
		return ecmApi;
	}
	
	/**
	 * ECM 서버에 파일 업로드 한다.
	 * 
	 * @param insourceId
	 * @param fileName
	 * @param is
	 * @param size
	 * @return EcmApiResponse
	 * @throw EcmApiException
	 */
	public EcmApiResponse upload(String insourceId, String docType, String fileName, InputStream is, long size, String createTime) {
		logger.debug("in ecm upload service");

		EcmApi ecmApi = null;
		EcmApiResponse ecmApiRes = null;
 		
		try {
			// ECM 연결
			ecmApi = connect();
			 
			// 마이그레이션용 코드(ECM_PROFILE 테이블에 mig_time 이라는 키값으로 실제 파일 생성 날짜를 저장한다)
			ProfileList profileList = new ProfileList();
			if (createTime != null)
				profileList.setData("mig_time", createTime);
			 
			// ECM 파일 업로드
			if (ecmApi != null){
				ecmApiRes = ecmApi.upload(insourceId, docType, insourceId, fileName, size, is, "", 0, profileList);
			}
		}catch(EcmApiException e) {
			throw e;
		} catch (Exception e) {
			throw new EcmApiException("Set Data in ProfileList Failed", e);
		}finally {
			close(ecmApi);
		}
		
		return ecmApiRes;
	}
	
	/**
	 * ECM서버로부터 파일을 다운로드한다.
	 * 
	 * @param fileId
	 * @param listener
	 * @return EcmApiResponse
	 * @throw EcmApiException
	 */
	public EcmApiResponse download(String fileId, DownloadListener listener) {
		logger.debug("in ecm download service");
		
		EcmApi ecmApi = null;
		EcmApiResponse ecmApiRes = null;
		
		try {
			// ECM 연결
			 ecmApi = connect();
			 
			 // 파일 다운로드
			 if (ecmApi != null){
				 ecmApiRes = ecmApi.download(fileId, listener);
			 }
			 
		}catch(EcmApiException e){
			throw e;
		}finally {
			close(ecmApi);
		}	
		
		return ecmApiRes;
	}
	
	/**
	 * ECM서버로부터 파일을 다운로드한다.
	 * 
	 * @param fileId
	 * @param outputStream
	 * @return EcmApiResponse
	 * @throw EcmApiException
	 */
	public EcmApiResponse download(String fileId, OutputStream outputStream) {
		logger.debug("in ecm download service");
		
		EcmApiResponse ecmApiRes = null;
		EcmApi ecmApi = null;
		
		try {
			 ecmApi = connect();
			 
			 if (ecmApi != null){
				 ecmApiRes = ecmApi.download(fileId, outputStream);
			 }
			 
		} catch(EcmApiException e){
			throw e;
		} finally {
            close(ecmApi);
        }
		
		return ecmApiRes;		
	}
	
	private void close(EcmApi ecmApi) {
		if (ecmApi != null) {
            ecmApi.close();
            logger.debug("ecm server closed");
        }
	}
}
