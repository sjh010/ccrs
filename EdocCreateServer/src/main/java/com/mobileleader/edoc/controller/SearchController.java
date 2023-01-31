package com.mobileleader.edoc.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.groups.Default;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mobileleader.edoc.db.dto.EdocGrpTmpStrgMgmtDto;
import com.mobileleader.edoc.model.response.SearchResponse;
import com.mobileleader.edoc.service.DownloadService;
import com.mobileleader.edoc.service.SearchService;
import com.mobileleader.edoc.validation.ValidationGroup;

/**
 * 전자문서 조회 컨트롤러
 * 
 * @author yh.kim
 */
@RestController
@RequestMapping(value="/download")
public class SearchController {
	
	private Logger logger = LoggerFactory.getLogger(SearchController.class);
	
	@Autowired
	private SearchService searchTmpService;
	
	@Autowired
	private DownloadService downloadService;

	/**
	 * 임시저장 목록 조회
	 */
	@RequestMapping(value="/temp/list", method=RequestMethod.POST, 
					produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
	public SearchResponse getTempDataList(@Validated({ValidationGroup.SearchTmp.class, Default.class}) @RequestBody EdocGrpTmpStrgMgmtDto request) throws Exception {
		
		logger.info("\n---------------------/download/temp/list---------------------");
		logger.info("[SearchTempList Request] = {}", request.toString());
		
		SearchResponse response = searchTmpService.search(request);
		
		logger.info("[SearchResponse] = {}", response.toString());
		
		return response;
	}
	
	/**
	 * 임시저장 데이터 다운로드
	 */
	@RequestMapping(value="/temp/data", method=RequestMethod.POST)
	public void getTempData(@Validated({ValidationGroup.DownloadTmp.class, Default.class}) @RequestBody EdocGrpTmpStrgMgmtDto request, 
							@RequestHeader(value="User-Agent", required=false, defaultValue="") String userAgent, HttpServletResponse httpServletResponse) throws Exception {
		
		logger.info("\n---------------------/download/temp/data---------------------");
		logger.info("[SearchTempData Request] = {}", request.toString());
		
		String filePath = searchTmpService.searchSvrFilePath(request);
		
		downloadService.downloadToByte(filePath, userAgent, httpServletResponse);
	}

}
