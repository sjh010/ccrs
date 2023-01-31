package com.mobileleader.image.client.service;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mobileleader.image.client.Exception.UserHttpStatus;
import com.mobileleader.image.db.dto.ImageFileDto;
import com.mobileleader.image.server.model.request.UploadRequest;
import com.mobileleader.image.server.model.response.UploadResponse;
import com.mobileleader.image.util.JsonUtil;
import com.mobileleader.image.util.UserHttpClient;

/**
 * (클라이언트) 파일 업로드 서비스
 * 
 * @author bkcho@mobileleader.com
 * @since 2018.08.23
 *
 */
public class UploadService {
	private static final Logger logger = LoggerFactory.getLogger(UploadService.class);

	private UserHttpClient userHttpClient = new UserHttpClient();
	
	public UploadResponse upload(String url, UploadRequest req) {
		logger.debug("ecmUpload");
		UploadResponse res = new UploadResponse();
		
		MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
		
		StringBody stringBody1 = new StringBody(req.toString(), ContentType.APPLICATION_JSON);
		multipartEntityBuilder.addPart("scanInfo", stringBody1);
		
		// file data
		if(req.getImageFileDtos() != null) {
			
			for (ImageFileDto imageInfo : req.getImageFileDtos()) {
				try {
					String fileName = (imageInfo.getFileName() != null)? imageInfo.getFileName() : "";
					
					File file = new File(fileName);
					if (file.isFile()) {
						FileBody fileBody = new FileBody(file,ContentType.APPLICATION_OCTET_STREAM, URLEncoder.encode(file.getName(),"UTF-8"));
						multipartEntityBuilder.addPart(imageInfo.getFileName(), fileBody);
					}					
					
				} catch (UnsupportedEncodingException e) {
					logger.error("UnsupportedEncodingException : ", e);
					res.setResultCode(UserHttpStatus.CLIENT_SERVICE_ERROR.getStatusCode());
					res.setResultMessage("UnsupportedEncodingException: " + e.getMessage());
					return res;
				} catch (Exception e) {
					logger.error("file upload faild : ", e);
					res.setResultCode(UserHttpStatus.CLIENT_SERVICE_ERROR.getStatusCode());
					res.setResultMessage("file upload faild: " + e.getStackTrace());
					return res;
				}
			}
		}
		
		String result = userHttpClient.upload(url, multipartEntityBuilder);
				
		res = JsonUtil.getObject(result.toString(), UploadResponse.class);
		
		if (res == null) {
			res = new UploadResponse();
			res.setResultCode(UserHttpStatus.CLIENT_SERVICE_ERROR.getStatusCode());
			res.setResultMessage("UploadResponse object parsing error.");
		}
		
		return res;
	}

}
