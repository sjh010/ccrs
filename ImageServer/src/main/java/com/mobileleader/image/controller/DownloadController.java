package com.mobileleader.image.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mobileleader.image.exception.ServiceError;
import com.mobileleader.image.exception.ServiceException;
import com.mobileleader.image.server.model.request.DownloadRequest;
import com.mobileleader.image.service.DownloadService;

 
/**
 * ECM 파일 다운로드 컨트롤러 	
 * @since 2018.08.20
 * @author bkcho@mobileleader.com
 *
 */
@Controller
@RequestMapping(value = "/download")
public class DownloadController extends ExceptionCaughtController{
	
	private static final Logger logger = LoggerFactory.getLogger(DownloadController.class);

	@Autowired
	DownloadService downloadService;
		
	/**
	 * ECM 파일 다운로드 (GET)
	 * http://localhost:8080/download?fileId=201803281656450h
	 * 
	 * @return image stream
	 * @throws Exception 
	 */
	@RequestMapping(value="", method={RequestMethod.GET}, 
					produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})	// 응답 타입
	@ResponseBody
	public void streamGet(HttpServletRequest req, HttpServletResponse res, @RequestParam("fileId") String fileId) throws Exception {
		logger.info("\r\n----------------------------------------------------------------------------------------------------------------------------------------------");
		logger.info("/download");
		logger.info("fileId = {}", fileId);
		
		if(fileId == null || fileId.equals("")) {
			throw new ServiceException(ServiceError.BAD_REQUEST);
		}
		
		downloadService.getEcmFile(req, res, fileId);
	}
	
	/**
	 * ECM 파일 다운로드 (POST)
	 * http://localhost:8080/image/download
	 *  
	 * @param res
	 * @param req
	 */
	@RequestMapping(value = "", method = RequestMethod.POST,
			consumes = {MediaType.APPLICATION_JSON_VALUE},			// 요청타입
			produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE}	// 응답타입
			)
    @ResponseBody
    public void streamPost(HttpServletRequest req, HttpServletResponse res, @Validated @RequestBody DownloadRequest downloadReq) throws Exception {
		logger.info("\r\n----------------------------------------------------------------------------------------------------------------------------------------------");
		logger.info("/download");
		logger.info("DownloadRequest :\r\n" + downloadReq.toString());
		
		downloadService.getEcmFile(req, res, downloadReq.getFileId());
    }	
}
