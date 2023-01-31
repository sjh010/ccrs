package com.mobileleader.image.client.service;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mobileleader.image.server.model.request.DownloadRequest;
import com.mobileleader.image.server.model.response.DownloadResponse;
import com.mobileleader.image.util.UserHttpClient;

/**
 * (클라이언트) 파일 다운로드 서비스
 * 
 * @author bkcho@mobileleader.com
 * @since 2018.08.23
 *
 */
public class DownloadService {
	private static final Logger logger = LoggerFactory.getLogger(DownloadService.class);

	private UserHttpClient userHttpClient = new UserHttpClient();
	private boolean crypto = false;

	public DownloadService() {}
	public DownloadService(boolean crypto) {
		this.crypto = crypto;
	}

	// GET
	public DownloadResponse download(String url) {
		logger.debug("downlaod");
		return userHttpClient.download(url, null);
	}
	
	// POST
	public DownloadResponse download(String url, String json) {
		logger.debug("downlaod");
		return userHttpClient.download(url, json);
	}
	
	// POST
	public DownloadResponse download(String url, DownloadRequest req) {
		logger.debug("downlaod");
		return download(url, req.toString());
	}
	
}
