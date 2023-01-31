package com.mobileleader.edoc.monitoring.controller.common;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mobileleader.edoc.monitoring.service.FileAttachService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value="/edoc/file")
@Slf4j
public class FileAttachController {
	
    @Autowired
    FileAttachService fileAttachService;
	
	/**
	 * PDF 파일 다운로드
	 * 
	 * @param fileId ECM 파일 아이디
	 * @param docTitle 서식명
	 * @return
	 */
	@RequestMapping(value = "/download/pdf", method = {RequestMethod.GET, RequestMethod.POST}, produces = "text/plain;charset=UTF-8")
	public ModelAndView downloadPdf(String fileId, String docTitle) {
		logger.info("download PDF - fileId : {}, docTitle : {}", fileId, docTitle);
		
		HashMap<String, Object> map = fileAttachService.downloadPdf(fileId, docTitle);
		
		return new ModelAndView("downloadView", "map", map);
	
	}
	
}