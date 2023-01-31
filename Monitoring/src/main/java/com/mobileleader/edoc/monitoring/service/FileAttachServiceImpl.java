package com.mobileleader.edoc.monitoring.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import com.mobileleader.image.client.service.DownloadService;
import com.mobileleader.image.server.model.request.DownloadRequest;
import com.mobileleader.image.server.model.response.DownloadResponse;
import com.mobileleader.webform.common.exception.ServiceError;
import com.mobileleader.webform.common.exception.ServiceException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class FileAttachServiceImpl implements FileAttachService {
	
	private Environment env;

	@Override
	public HashMap<String, Object> downloadPdf(String fileId, String docTitle) {
		HashMap<String, Object> map = new HashMap<String, Object>();
	
		File file = downloadPdf(fileId);
		
		long fileSize = file.length();
		
		map.put("fileName", docTitle + ".pdf");
		map.put("fileSize", (int) fileSize);
		
		FileInputStream fis;
		try {
			fis = new FileInputStream(file);
			map.put("inputStream", fis);
		} catch (FileNotFoundException e) {
			throw new ServiceException(ServiceError.FILE_NOT_EXIST);
		}
		
		return map;
	}
	
	private File downloadPdf(String fileId) {
		String hostname = "";
		
		try {
			 hostname = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			logger.error("get hostname error", e);
		}
		
		String instanceName = System.getProperty("weblogic.Name");
		
		StringBuilder sb = new StringBuilder("image.download");
		sb.append("_").append(hostname).append("_").append(instanceName);
		
		logger.info("Property : {}", sb.toString());
		
		String url = env.getProperty(sb.toString());
		
		logger.info("URL : {}", url);
		
		DownloadRequest req = new DownloadRequest(fileId);
		
		DownloadService downloadService = new DownloadService(false);

		DownloadResponse res = new DownloadResponse();
			
		res = downloadService.download(url, req);
		
		File file = null;
		
		logger.info("Response : resultCode : {}, resultMessage : {}", res.getResultCode(), res.getResultMessage());
		
		if (res.getResultCode() == 200) {
			try {
				String fileName = env.getProperty("image.rootPath") + File.separator + FilenameUtils.getBaseName(res.getFileName());
				
				file = new File(fileName);
				
				FileOutputStream fos = new FileOutputStream(file);
				
				fos.write(res.getBuffer());
				fos.close();
				
				return file;
			} catch (IOException e) {
				throw new ServiceException(ServiceError.FILE_IO_ERROR, e);
			}
		}
		
		return null;
	}
}
