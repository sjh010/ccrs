package com.mobileleader.edoc.controller;

import java.io.File;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mobileleader.edoc.exception.EdocServerException;
import com.mobileleader.edoc.exception.EdocServerStatus;
import com.mobileleader.edoc.properties.EdocServiceProp;
import com.mobileleader.edoc.service.DownloadService;

/**
 * 전자서식 다운로드 컨드롤러
 * 
 * @author yh.kim
 */
@RestController
public class EformDownloadController {	
	
	private Logger logger = LoggerFactory.getLogger(EformDownloadController.class);
	
	@Autowired
	private DownloadService downloadService;
	

	/* ex) .../form/A00001.pdf 
	 * 폴더+파일 2depth만 가능
	 */
	@RequestMapping(value= {"/{folderName}/{fileName:.+}"}, method= {RequestMethod.GET})
	public void downloadEform(@PathVariable("folderName") String folderName, @PathVariable("fileName") String fileName, 
								@RequestHeader(value="User-Agent", required=false, defaultValue="") String userAgent, HttpServletResponse response) throws Exception {
		
		logger.info("\n---------------------EformDownload---------------------");
		logger.debug("browser = {}", userAgent);

		downloadService.downloadToStream(validateFilePath(folderName, fileName), userAgent, response);
	}
	
	private String validateFilePath(String folderName, String fileName) {
		
		if(folderName == null || folderName.equals("") || fileName == null || fileName.equals("")) {
			throw new EdocServerException(EdocServerStatus.BAD_REQUEST);
		}
		
		File formFile = new File(EdocServiceProp.EFORM_ROOT_PATH() + folderName, fileName);
		String formFilePath = formFile.getAbsolutePath();
		
		logger.debug("[FormFile Path] = {}", formFilePath);

		if(!formFile.exists()) {
			throw new EdocServerException(EdocServerStatus.FORM_FILE_NOT_EXIST);
		}
		
		return formFilePath;
	}
	
	
	/* ex) .../eform?filePath=form/A00001.pdf 
	 * 모든 경로 가능
	 */
//	@RequestMapping(value="/eform", method=RequestMethod.GET)
//	public void downloadEform(@RequestParam("filePath") String filePath, @RequestHeader("User-Agent") String userAgent, HttpServletResponse response) throws IOException {
//		
//		logger.info("\n---------------------/eform---------------------");
//		
//		filePath = EdocServerProp.EFORM_ROOT_PATH() + filePath;
//		logger.info("[FilePath] = {}", filePath);
//		
//		File formFile = new File(filePath);
//		if(!formFile.exists()) {
//			throw new EdocServerException(EdocServerError.FILE_NOT_EXIST, "Form File Not Exist");
//		}
//		
//		new FileDownloadExecutor(userAgent).stream(formFile, response);
//	}
}
