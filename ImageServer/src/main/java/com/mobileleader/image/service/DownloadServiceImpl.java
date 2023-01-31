package com.mobileleader.image.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecm.api.common.exception.EcmApiException;
import com.ecm.api.common.model.EcmApiResponse;
import com.inzisoft.util.AESCrypto;
import com.inzisoft.util.InziCryptoException;
import com.mobileleader.image.exception.ServiceError;
import com.mobileleader.image.exception.ServiceException;
import com.mobileleader.image.util.MediaTypes;

/**
 * ECM 데이터 다운로드 처리 서비스 구현 클래스
 */
@Service
public class DownloadServiceImpl implements DownloadService {
	private static final Logger logger = LoggerFactory.getLogger(DownloadServiceImpl.class);

	@Autowired(required=true)
	private EcmService ecmService;
	
	@Override
	public EcmApiResponse getEcmFile(HttpServletRequest request, HttpServletResponse response, String fileId) throws Exception {
		logger.debug("in getEcmFile");
		
		EcmApiResponse ecmApiRes = null;
		DataInputStream dis = null;
		
		try {
			// 1. ECM서버로부터 파일 다운로드
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ecmApiRes = ecmService.download(fileId, outputStream);
			
			if(ecmApiRes == null || !ecmApiRes.isSuccess()) {
				throw new ServiceException(ServiceError.ECM_DOWNLOAD_FAILED, String.format("ECM_ErrorCode : %d \t ECM_ErrorMsg : %s", ecmApiRes.getErrorCode(), ecmApiRes.getErrorMessage()));
			}

			// 파일 path에서 파일 name으로 변경하여 저장.
			String ecmFileName = ecmApiRes.getFileName();
			String fileName = Paths.get(ecmFileName).getFileName().toString();				
			
			logger.debug("fileId: " + ecmApiRes.getFileId());
			logger.debug("fileName: " + fileName);
			logger.debug("ori file size: " +  outputStream.size());


			int bufferSize;
			InputStream is = null;
				
            // 2. 암호화파일 복호화
            byte[] dec = AESCrypto.deryptFileMem(outputStream.toByteArray());
            if (dec != null) {
            	bufferSize = dec.length;
	            is = new ByteArrayInputStream(dec);
	            
	            logger.debug("dec file size: " + bufferSize);
            } else {
            	// 인지 복호화가 안되면 암호화가 안된 것으로 간주하여 원본을 그대로 내보낸다.
            	bufferSize = outputStream.size();
            	is = new ByteArrayInputStream(outputStream.toByteArray()); 		           
            }
            
            // 3. HttpServletResponse 값 setting
            response.setContentLength(bufferSize);
            response.setHeader("Content-Disposition", "attachment; filename=\"" + encodeFileName(request, fileName) + "\";");
    	    response.setHeader("Content-Transfer-Encoding", "binary");
    	    
    	    MediaTypes types = MediaTypes.fromFileName(ecmApiRes.getFileName());
            response.setContentType(types.getMime());
            
            dis = new DataInputStream(is);
            
            // 4. 파일 반환
            int retSize = HttpFileDonwloadListener.copyStream(dis, response.getOutputStream(), bufferSize);
            
            logger.debug("ret size: " + retSize);
            
		}catch(EcmApiException e) {
			throw new ServiceException(ServiceError.ECM_ERROR, String.format("[Ecm Api Error] ECM_ErrorCode : %d \t ECM_ErrorMsg : %s", e.getServiceError().getCode(), e.getMessage()));
		}catch(InziCryptoException e) {
			throw new ServiceException(ServiceError.FILE_DECRYPT_ERROR, String.format("InziCrypto_ErrorCode : %d \t InziCrypto_ErrorMsg : %s", e.getErrorCode(), e.getMessage()));
		}catch(IOException e) {
			throw new ServiceException(ServiceError.FILE_IO_ERROR, e.getMessage());
		}finally {
			if(dis != null) {
				dis.close();
			}
		}
		
		return ecmApiRes;
	}
	
	/*
	 * 브라우저 별 fileName 인코딩
	 */
	private String encodeFileName(HttpServletRequest request, String filename) {    	
    	String encodedFilename = null;
    	
    	String browser = getBrowser(request);
    	
    	try {
    		if ("MSIE".equals(browser)) {
    			encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
    		} else if ("Firefox".equals(browser) || "Opera".equals(browser)) {
    			encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
    		} else if ("Chrome".equals(browser)) {
    			StringBuilder sb = new StringBuilder();
    			for (int i = 0; i < filename.length(); i++) {
    				char c = filename.charAt(i);
    				if (c > '~') {
    					sb.append(URLEncoder.encode("" + c, "UTF-8"));
    				} else {
    					sb.append(c);
    				}
    			}
    			encodedFilename = sb.toString();
    		} else {
    			encodedFilename = new String(filename.getBytes("UTF-8"), "ISO-8859-1");  
    		}
    	} catch (Exception e) {
			throw new ServiceException(ServiceError.ENCODING_ERROR);
		}
    	
    	return encodedFilename;
    }

	/*
	 * 브라우저 구분
	 */
	private String getBrowser(HttpServletRequest request) {
		
    	String userAgent = (request.getHeader("User-Agent") != null)? request.getHeader("User-Agent") : "";
    	
    	if (userAgent.indexOf("MSIE") > -1 || userAgent.indexOf("Trident") > -1) {
    		// IE 버전 별 체크 : Trident/7.0 (IE 11), Trident/6.0 (IE 10), Trident/5.0 (IE 9), Trident/4.0 (IE 8)
    		return "MSIE";
    	} else if (userAgent.indexOf("Chrome") > -1) {
    		return "Chrome";
    	} else if (userAgent.indexOf("Opera") > -1) {
    		return "Opera";
    	} else if (userAgent.indexOf("Firefox") > -1) {
    		return "Firefox";
    	} else {
    		return "Safari";
    	}
    }
}
