package com.mobileleader.image.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mobileleader.image.server.model.response.KeyGenerateResponse;
import com.mobileleader.image.service.KeyGenerateService;

/**
 * 통합 전자문서키(mainKey) 채번 컨트롤러
 * 
 */
@Controller
@RequestMapping(value="/generate")
public class KeyGenerateController extends ExceptionCaughtController {
	
	@Autowired
	private KeyGenerateService keyGenService;
	
	private Logger logger = LoggerFactory.getLogger(KeyGenerateController.class);
	
	/**
	 * mainKey 생성 (GET)
	 * http://localhost:8080/generate/mainkey
	 * 
	 * @return	KeyGenerateResponse
	 * @throws 	Exception
	 */
	@RequestMapping(value="/mainkey", method=RequestMethod.GET)
	@ResponseBody
	public KeyGenerateResponse generateKey_Get() throws Exception {
		logger.info("\r\n----------------------------------------------------------------------------------------------------------------------------------------------");
		logger.info("GET: /generate/mainkey");
		
		KeyGenerateResponse res = keyGenService.generateKey();
		
		logger.info("KeyGenerateResponse :\r\n" + res.toString());
		
		return res;
	}
	
	/**
	 * mainKey 생성 (POST)
	 * http://localhost:8080/generate/mainkey
	 * 
	 * @param 	request
	 * @return	KeyGenerateResponse
	 * @throws 	Exception
	 */
//	@RequestMapping(value="/mainkey", method=RequestMethod.POST)
//	@ResponseBody
//	public KeyGenerateResponse generateKey_Post() throws Exception {
//		logger.info("\r\n----------------------------------------------------------------------------------------------------------------------------------------------");
//		logger.info("POST : /generate/mainkey");
//		//logger.info("Request:\r\n" + request.toString());
//		
//		KeyGenerateResponse res = keyGenService.generateKey();
//		
//		logger.info("KeyGenerateResponse :\r\n" + res.toString());
//		
//		return res;
//	}

}
