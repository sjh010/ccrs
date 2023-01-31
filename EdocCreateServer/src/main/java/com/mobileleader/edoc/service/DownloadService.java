package com.mobileleader.edoc.service;

import javax.servlet.http.HttpServletResponse;

/**
 * 다운로드 Service Interface
 * 
 * @author yh.kim
 *
 */
public interface DownloadService {
	
	public void downloadToByte(String filePath, String userAgent, HttpServletResponse httpServletResponse) throws Exception;
	
	public void downloadToStream(String filePath, String userAgent, HttpServletResponse httpServletResponse) throws Exception;
}
