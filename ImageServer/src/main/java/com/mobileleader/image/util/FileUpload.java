package com.mobileleader.image.util;

import java.io.FileOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
 
public class FileUpload {
	
	private static final Logger logger = LoggerFactory.getLogger(FileUpload.class);
	
	private FileOutputStream fos;
	
	public void writeFile(MultipartFile file, String path, String fileName) {
		
		// file 속성에 관한 자료
		// http://winmargo.tistory.com/102
		try {			
			byte[] fileData = file.getBytes();

			fos = new FileOutputStream(path + fileName);
			fos.write(fileData);

			logger.info("filename: " + path + fileName);
			logger.info("FILE_UPLOAD_SUCCESS");
			
		} catch (IOException e) {			
			logger.warn("FILE_UPLOAD_ERROR: " + e.toString());
		} finally{
			if(fos != null){                
                try{
                    fos.close();
                }catch(Exception e){
                	logger.warn("ERROR: " + e.toString());
                }                 
            }
		}
	}
}
