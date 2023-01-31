package com.mobileleader.image.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mobileleader.image.server.model.request.SearchRequest;
import com.mobileleader.image.server.model.response.SearchResponse;
import com.mobileleader.image.service.SearchService;

/**
 * 이미지 조회 컨트롤러 
 * 
 * @author bkcho@mobileleader.com
 * @since 2018.08.22
 *
 */
@Controller
@RequestMapping(value = "/search")
public class SearchController extends ExceptionCaughtController {
	
	private static final Logger logger = LoggerFactory.getLogger(SearchController.class);

	@Autowired
	SearchService searchService;
	
	/**
	 * mainkey 를 이용한 fileId 리스트 조회 
	 * @param req
	 * @param resp
	 * @param mainkey
	 * @return
	 * 
	 *  http://localhost:8080/image/search
	 *  {
	   		"imageFileDto" : {
				"mainKey" : "222222",
				"docId" : "333333"
			}
		}
	 * @throws Exception 
	 * 
	 */
	@RequestMapping(value = "", method = RequestMethod.POST, 
			produces = {MediaType.APPLICATION_JSON_VALUE},
			consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public SearchResponse searchMainKey(@Validated @RequestBody SearchRequest searchReq) throws Exception  {
		logger.info("\r\n----------------------------------------------------------------------------------------------------------------------------------------------");
		logger.info("/search/mainkey");
		logger.info("SearchRequest :\r\n" + searchReq.toString());

		searchReq.getImageFileDto().setDeleteYn("N");
		
		SearchResponse res = searchService.search(searchReq);
				
		logger.info("SearchResponse :\r\n" + res.toString());
		return res;
    }
	
	@RequestMapping(value = "/all", method = RequestMethod.POST, 
			produces = {MediaType.APPLICATION_JSON_VALUE},
			consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public SearchResponse searchAll(@Validated @RequestBody SearchRequest searchReq) throws Exception  {
		logger.info("\r\n----------------------------------------------------------------------------------------------------------------------------------------------");
		logger.info("/search/all");
		logger.info("SearchRequest :\r\n" + searchReq.toString());
		
		searchReq.getImageFileDto().setVersionInfo(999).setDeleteYn("Y");

		SearchResponse res = searchService.search(searchReq);
				
		logger.info("SearchResponse :\r\n" + res.toString());
		return res;
    }
	
	/**
	 * 
	 * @param searchReq
	 * @return
	 */
//	@RequestMapping(value = "mainkeyList", method = RequestMethod.POST,
//			consumes = {MediaType.APPLICATION_JSON_VALUE},
//			produces = {MediaType.APPLICATION_JSON_VALUE})
//    @ResponseBody
//    public SearchListResponse searchMainKeyList(@RequestBody SearchRequest searchReq) {
//		logger.info("\r\n----------------------------------------------------------------------------------------------------------------------------------------------");
//		logger.info("/search/mainkeyList");
//		logger.info("SearchRequest:\r\n" + searchReq.toString());
//				
//		SearchListResponse res = new SearchListResponse();
//		res.setResultCode(0);
//		res.setResultMessage("unknow error");
//	 
//		if (searchReq != null) {
//			for (String mainKey : searchReq.getMainKeys()) {
//				MainkeyListResponse searchRes = searchService.searchFileId(mainKey);
//				res.setImageInfos(searchRes);
//			}
//			res.setResultCode(200);
//			res.setResultMessage("OK");
//		}
//		
//		logger.info("SearchListResponse:\r\n" + res.toString());
//		return res;
//    }

}
