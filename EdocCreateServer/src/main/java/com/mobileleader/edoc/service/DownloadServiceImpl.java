package com.mobileleader.edoc.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.mobileleader.edoc.exception.EdocServerException;
import com.mobileleader.edoc.exception.EdocServerStatus;
import com.mobileleader.edoc.type.MediaTypes;

/**
 * 파일 다운로드 Service
 * 
 * @author yh.kim
 */
@Service
public class DownloadServiceImpl implements DownloadService{
	
	private Logger logger = LoggerFactory.getLogger(DownloadServiceImpl.class);

	@Override
	public void downloadToByte(String filePath, String userAgent, HttpServletResponse httpServletResponse) throws Exception {
		logger.debug("in downlod to Byte");
		execute(new File(filePath), userAgent, httpServletResponse, "attachment");
	}

	@Override
	public void downloadToStream(String filePath, String userAgent, HttpServletResponse httpServletResponse) throws Exception {
		logger.debug("in downlod to Stream");
		execute(new File(filePath), userAgent, httpServletResponse, "inline");
	}
	
	private void execute(File file, String userAgent, HttpServletResponse response, String downloadType) throws IOException {
		logger.info("[Execute File Download]");
		
		String fileName = file.getName();
		logger.debug("fileName = {}", fileName);
		
		MediaTypes types = MediaTypes.fromFileName(fileName);
		response.setContentType(types.getMime());		
		
		response.setHeader("Content-Disposition", downloadType + "; filename=\"" + encodeFileName(fileName, userAgent) + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setContentLength((int)file.length());
		
		OutputStream out = response.getOutputStream();
		FileInputStream fis = null;
		
		try {
			fis = new FileInputStream(file);
			FileCopyUtils.copy(fis, out);
		}catch(Exception e) {
			throw e;
		} finally {
			if(fis != null) {
				fis.close();
			}
			out.flush();
			logger.info("[File Download Complete]");
		}
	}
	
	private String encodeFileName(String fileName, String userAgent) {
		String encodedFileName = "";
		String browser = getBrowser(userAgent);
		
		try {
    		if ("MSIE".equals(browser)) {
    			encodedFileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
    		} else if ("Firefox".equals(browser) || "Opera".equals(browser)) {
    			encodedFileName = "\"" + new String(fileName.getBytes("UTF-8"), "8859_1") + "\"";
    		} else if ("Chrome".equals(browser)) {
    			StringBuilder sb = new StringBuilder();
    			for (int i = 0; i < fileName.length(); i++) {
    				char c = fileName.charAt(i);
    				if (c > '~') {
    					sb.append(URLEncoder.encode("" + c, "UTF-8"));
    				} else {
    					sb.append(c);
    				}
    			}
    			encodedFileName = sb.toString();
    		} else {
    			encodedFileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");  
    		}
    	} catch (Exception e) {
    		throw new EdocServerException(EdocServerStatus.UNSUPPORTED_ENCODING_TYPE, e);
		}
		
		return encodedFileName;
	}	
	
	private String getBrowser(String userAgent) {
    	
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
