package com.mobileleader.image.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mobileleader.image.exception.ServiceError;
import com.mobileleader.image.exception.ServiceException;
import com.mobileleader.image.server.model.request.UploadRequest;
import com.mobileleader.image.server.model.response.UploadResponse;
import com.mobileleader.image.service.UploadService;
import com.mobileleader.image.util.JsonUtil;
import com.mobileleader.image.validation.UpdateValidator;

/**
 * ECM 파일 업로드 컨트롤러 
 * 
 * @author bkcho@mobileleader.com
 * @since 2018.08.13
 *
 */

@Controller
@RequestMapping(value = "/upload")
public class UploadController extends ExceptionCaughtController {
	
	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

	@Autowired
	@Qualifier("upload")
	private UploadService uploadService;
	
	@Autowired
	@Qualifier("update")
	private UploadService updateService;
	
	@Autowired
	private UpdateValidator updateValidator;
	
	/*
	 * 문서 업로드 
	 */
	@RequestMapping(value = "", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public UploadResponse newUpload(@Validated @ModelAttribute("uploadReq") UploadRequest uploadRequest, @ModelAttribute("fileMap") Map<String, MultipartFile> fileMap) throws Exception {
		logger.info("\r\n----------------------------------------------------------------------------------------------------------------------------------------------");
		logger.info("/upload");
		logger.info("UploadRequest :\r\n" + uploadRequest.toString());

		UploadResponse res = uploadService.upload(uploadRequest, fileMap);
		res.setResultMessage("upload successful.");
		
		logger.debug("UploadResponse:\r\n" + res.toString());

		return res;
	}
	
	/*
	 * 보완스캔 업데이트
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public UploadResponse update(@ModelAttribute("uploadReq") UploadRequest uploadRequest,  BindingResult bindingResult, @ModelAttribute("fileMap") Map<String, MultipartFile> fileMap) throws Exception {
		logger.info("\r\n----------------------------------------------------------------------------------------------------------------------------------------------");
		logger.info("/upload/update");
		logger.info("UpdateRequest(UploadRequest) :\r\n" + uploadRequest.toString());
		
		//request validate
		updateValidator.validate(uploadRequest, bindingResult);
		if(bindingResult.hasErrors()) {
			throw new BindException(bindingResult);
		}
		
		UploadResponse res = updateService.upload(uploadRequest, fileMap);
		res.setResultMessage("update successful.");
		
		logger.debug("UpdateResponse(UploadResponse) :\r\n" + res.toString());
		
		return res;
	}
	
	/* 
	 * 메타 정보 처리 
	 */
	@ModelAttribute("uploadReq")
	private UploadRequest getUploadReq(MultipartHttpServletRequest request) {
		String scanInfo = request.getParameter("scanInfo");
		logger.debug("Upload Request meta information :\r\n" + JsonUtil.jsonPrettyPrint(scanInfo));
		
		UploadRequest req = JsonUtil.getObject(scanInfo, UploadRequest.class);
		if(req == null) {
			throw new ServiceException(ServiceError.BAD_REQUEST, "scanInfo(json data) is required");
		}
		
		if(req.getImageStoreDto() == null) {
			throw new ServiceException(ServiceError.INVALID_REQUEST_PARAMETER, "ImageStoreInfo is required");
		}
		
		if(req.getImageFileDtos() == null || req.getImageFileDtos().size() == 0) {
			throw new ServiceException(ServiceError.INVALID_REQUEST_PARAMETER, "ImageFileInfo is required");
		}
		
		return req;
	}
	
	/* 
	 * 업로드파일 처리
	 */
	@ModelAttribute("fileMap")
	private Map<String, MultipartFile> getFileList(MultipartHttpServletRequest request){
		
		Map<String, MultipartFile> fileMap = new HashMap<String, MultipartFile>();
		
		Iterator<String> itr =  request.getFileNames();
		
		while(itr.hasNext()) {
			String nameKey = itr.next();
			nameKey = nameKey.replace("\\", "/");
			
			logger.debug("multipart key : " + nameKey);
			logger.debug("multpartContentType : " + request.getMultipartContentType(nameKey));
			logger.debug("contentType : " + request.getContentType());
			
			MultipartFile mpf = request.getFile(nameKey);
			String fileName = FilenameUtils.getName(mpf.getName());
			fileMap.put(fileName, mpf);
		}
		return fileMap;
	}
}
