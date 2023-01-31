package com.mobileleader.image.service;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecm.api.DownloadHeader;
import com.ecm.api.DownloadListener;
import com.mobileleader.image.exception.ServiceError;
import com.mobileleader.image.exception.ServiceException;
import com.mobileleader.image.util.MediaTypes;

public class HttpFileDonwloadListener implements DownloadListener {

	private static final Logger logger = LoggerFactory.getLogger(HttpFileDonwloadListener.class);
	private static final int BUFFER_SIZE = 102400;
	
	private String userAgent;
	private HttpServletResponse response;
	
	public HttpFileDonwloadListener(String userAgent, HttpServletResponse response) {
		this.userAgent = userAgent;
		this.response = response;
	}
	
	public void transferStream(DownloadHeader dnHeader, DataInputStream dis) {
		
		String fileName = dnHeader.getFileName();
		String metaData = dnHeader.getMetaData();
		
		logger.debug("fileName: " + fileName);
		logger.debug("metaData: " + metaData);
		
        try {
        	if (userAgent.indexOf("MSIE 5.5") > -1) { // MS IE 5.5 이하
        		fileName = URLEncoder.encode(fileName,"UTF-8").replaceAll("\\+", "%20");
        	} else if (userAgent.indexOf("MSIE") > -1) { // MS IE (보통은 6.x 이상 가정)
        		fileName = URLEncoder.encode(fileName,"UTF-8").replaceAll("\\+", "%20");
        	} else if (userAgent.indexOf("Trident") > -1) { //MS IE 11
        		fileName = URLEncoder.encode(fileName,"UTF-8").replaceAll("\\+", "%20");
        	} else { // 모질라나 오페라
        		fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");                   
        	}
		} catch (UnsupportedEncodingException e) {
			throw new ServiceException(ServiceError.ENCODING_ERROR, e);
		}                                                                                    

		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
	    response.setHeader("Content-Transfer-Encoding", "binary");
	    MediaTypes types = MediaTypes.fromFileName(fileName);
        response.setContentType(types.getMime());
        
        int fileSize = dnHeader.getFileSize();
        logger.debug("fileSize: " + fileSize);
        
        response.setContentLength(fileSize);
        
        try {
			copyStream(dis, new BufferedOutputStream(response.getOutputStream()), fileSize);
		} catch (IOException e) {			
			throw new ServiceException(ServiceError.FILE_IO_ERROR, e);
		} finally {
				try {
					dis.close();
				} catch (IOException e) {
					logger.error("DataInputStream Close Failed");
				}
		}
	}
	
	public static int copyStream(DataInputStream dis, OutputStream out, int size) throws IOException {
		
		int byteCount = 0;
		byte[] buffer = new byte[BUFFER_SIZE];
		int bytesRead = -1;
		
		while (byteCount < size) {
			
			int left = size - byteCount;
			int readSize = (BUFFER_SIZE < left ? BUFFER_SIZE : left);
			bytesRead = dis.read(buffer, 0, readSize);
			if(bytesRead < 0) {
				throw new IOException("Invalid File size");
			}
			out.write(buffer, 0, bytesRead);
			byteCount += bytesRead;
		}
		
		out.flush();
		return byteCount;
	}
}
