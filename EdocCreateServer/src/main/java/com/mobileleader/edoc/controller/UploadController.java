package com.mobileleader.edoc.controller;

import javax.validation.groups.Default;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mobileleader.edoc.db.dto.EdocGrpBzwkInfoDto;
import com.mobileleader.edoc.db.dto.EdocGrpTmpStrgMgmtDto;
import com.mobileleader.edoc.exception.EdocServerException;
import com.mobileleader.edoc.exception.EdocServerStatus;
import com.mobileleader.edoc.model.response.UploadResponse;
import com.mobileleader.edoc.service.UploadService;
import com.mobileleader.edoc.util.JsonUtils;
import com.mobileleader.edoc.validation.ValidationGroup;

/**
 * 전자문서 업로드  Controller
 * 
 * @author yh.kim
 */
@RestController
@RequestMapping(value="/edoc")
public class UploadController {
	
	private Logger logger = LoggerFactory.getLogger(UploadController.class);
	
	@Autowired
	@Qualifier("uploadEdocData")
	private UploadService uploadEdocData;

	@Autowired
	@Qualifier("uploadTempData")
	private UploadService uploadTempDataService;

	/**
	 * 전자문서 생성 요청 처리
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public UploadResponse upload(@Validated({ValidationGroup.Upload.class, Default.class}) @ModelAttribute("bzwkDto") EdocGrpBzwkInfoDto request, 
									@ModelAttribute("file") MultipartFile file) throws Exception{

		logger.info("\r\n---------------------/edoc/upload---------------------");
		logger.info("\r\n[UploadEdocData Request] = {}", request.toString());
		
		UploadResponse response = uploadEdocData.upload(request, file);
		
		logger.info("\r\n[UploadResponse] = {}", response.toString());
		
		return response;
	}
	
	/**
	 * 임시 저장 요청 처리
	 */
	@RequestMapping(value = "/upload/tempData", method = RequestMethod.POST)
	public UploadResponse saveTempData(@Validated({ValidationGroup.SaveTmp.class, Default.class}) @ModelAttribute("tmpDto") EdocGrpTmpStrgMgmtDto request, 
										@ModelAttribute("file") MultipartFile file) throws Exception {

		logger.info("\r\n---------------------/edoc/upload/tempData---------------------");
		logger.info("\r\n[UploadTempData Request] = {}", request.toString());
		
		UploadResponse response = uploadTempDataService.upload(request, file);
		
		logger.info("\r\n[UploadResponse] = {}", response.toString());
		
		return response;
	}
	
	@ModelAttribute("bzwkDto")
	private EdocGrpBzwkInfoDto getBzwkInfoReq(MultipartHttpServletRequest request) {
		String metaInfo = request.getParameter("data");
		logger.debug("metaInfo = {}", metaInfo);
		
		EdocGrpBzwkInfoDto req = JsonUtils.fromJson(metaInfo, EdocGrpBzwkInfoDto.class);
		return req;
	}
	
	@ModelAttribute("tmpDto")
	private EdocGrpTmpStrgMgmtDto getTmpMgmtReq(MultipartHttpServletRequest request) {
		String metaInfo = request.getParameter("data");
		logger.debug("metaInfo = {}", metaInfo);
		
		EdocGrpTmpStrgMgmtDto req = JsonUtils.fromJson(metaInfo, EdocGrpTmpStrgMgmtDto.class);
		return req;
	}
	
	@ModelAttribute("file")
	private MultipartFile getFile(@RequestParam(name="file", required=false) MultipartFile file){
		if(file == null) {
			throw new EdocServerException(EdocServerStatus.BAD_REQUEST, "file is required");
		}
		
		String fileName = file.getOriginalFilename();
		logger.debug("multipart file name = {}", fileName);
		logger.debug("multipart file size = {}", file.getSize());
		
		if(!fileName.toLowerCase().endsWith(".zip")) {
			throw new EdocServerException(EdocServerStatus.INVALID_REQUEST, "invalid file type");
		}
		
		return file;
	}
}
