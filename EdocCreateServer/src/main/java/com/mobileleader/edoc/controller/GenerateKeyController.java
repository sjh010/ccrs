package com.mobileleader.edoc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mobileleader.edoc.db.dto.EdocGrpBzwkInfoDto;
import com.mobileleader.edoc.model.response.UploadResponse;
import com.mobileleader.edoc.service.GenerateKeyService;

/**
 * 전자문서그룹 인덱스번호 생성 컨트롤러 
 * 
 * @author yh.kim
 *
 */
@RestController
public class GenerateKeyController {
	
	private Logger logger = LoggerFactory.getLogger(GenerateKeyController.class);
	
	@Autowired
	private GenerateKeyService edocKeyService;
	
	
	@RequestMapping(value = "/edoc/getKey", method = RequestMethod.POST, 
			produces = {MediaType.APPLICATION_JSON_VALUE},
			consumes = {MediaType.APPLICATION_JSON_VALUE})
	public UploadResponse generateEdocGrpKey(@Validated @RequestBody EdocGrpBzwkInfoDto request) throws Exception {
		logger.info("\n---------------------/edoc/getKey---------------------");
		logger.info("[GenerateEdocKey Request] = {}", request.toString());
		
		UploadResponse response = edocKeyService.generate(request);
		
		logger.info("[GenerateEdocKey Response] = {}", response.toString());
	
		return response;
	}
}
