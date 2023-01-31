package com.mobileleader.edoc.monitoring.support.view;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.web.servlet.view.AbstractView;

public class DownloadView extends AbstractView {
	
	public DownloadView() {
		//setContentType("application/octet-stream");
		setContentType("application/download; charset=utf-8");
	}
	
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		@SuppressWarnings("unchecked")
        HashMap<String, Object> map = (HashMap<String, Object>) model.get("map");
		
		String fileName = (String) map.get("fileName");
		int fileSize = (Integer) map.get("fileSize");
        
        response.setContentType(getContentType());
        response.setContentLength(fileSize);
        
        String userAgent = request.getHeader("User-Agent");
        if (userAgent.indexOf("MSIE 5.5") > -1) { // MS IE 5.5 이하
            fileName = URLEncoder.encode(fileName,"UTF-8").replaceAll("\\+", "%20");                                                                                    
	    } else if (userAgent.indexOf("MSIE") > -1) { // MS IE (보통은 6.x 이상 가정)
	        fileName = URLEncoder.encode(fileName,"UTF-8").replaceAll("\\+", "%20");
	    } else if (userAgent.indexOf("Trident") > -1) { //MS IE 11
	        fileName = URLEncoder.encode(fileName,"UTF-8").replaceAll("\\+", "%20");
	    } else { // 모질라나 오페라
	        fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");                   
	    }
 
        logger.info("encoded file name : " + fileName);
        
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary");
         
        OutputStream out = response.getOutputStream();
        InputStream is = (InputStream) map.get("inputStream");       
        
        try {        	
        	IOUtils.copy(is, out);  
        } catch(IOException e){
            logger.error("IOException", e);
        } catch(Exception e) {
        	logger.error("Exception", e);
        } finally{
            if (is != null) {
            	is.close();
            }
        }
         
        out.flush();
		
	}
}
