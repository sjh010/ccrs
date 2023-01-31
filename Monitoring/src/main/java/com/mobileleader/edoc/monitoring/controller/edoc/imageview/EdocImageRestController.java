package com.mobileleader.edoc.monitoring.controller.edoc.imageview;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mobileleader.edoc.monitoring.common.model.request.ImageSearchReqeust;
import com.mobileleader.edoc.monitoring.service.image.ImageServerService;
import com.mobileleader.image.client.service.SearchService;
import com.mobileleader.image.db.dto.ImageFileDto;
import com.mobileleader.image.server.model.request.SearchRequest;
import com.mobileleader.image.server.model.response.SearchResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value="/edoc/view")
@RequiredArgsConstructor
@Slf4j
public class EdocImageRestController {
	
	private final Environment env;
	
	@Autowired
	private ImageServerService imageServerService;
	
	/**
	 * 2019-07-18
	 * 신용회복위원회 전용으로 수정함
	 * by jhso@mobileleader.com
	 * 
	 * @param searchStoreListRequest 이미지 조회 검색폼
	 * @return
	 */
	@PostMapping(value="/list")
	@Secured("ROLE_D01_LIST")
	public HashMap<String, Object> list(@ModelAttribute("searchRequest") ImageSearchReqeust searchRequest) {

		logger.info("[/edoc/view/list] Request : {}", searchRequest.toString());

		return imageServerService.searchImageStoreList(searchRequest);	
	}
	
	/**
	 * 이미지 전체 조회 : 모든 버전의 이미지 + 삭제된 이미지 조회
	 * 
	 * @param mainKey
	 * @return
	 */
	@RequestMapping(value="/sform/all")
	@Secured("ROLE_D01_DTAL")
	public List<ImageFileDto> viewAllImage(@RequestParam("mainKey") String mainKey) {
		logger.info("/edoc/view/sform/all");
		logger.info("mainKey : {}", mainKey);
		
		SearchRequest request = new SearchRequest().setImageFileDto(new ImageFileDto().setMainKey(mainKey).setVersionInfo(999));
		
		SearchService searchService = new SearchService();
		SearchResponse response = new SearchResponse();
		
		String hostname = "";
		
		try {
			 hostname = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			logger.error("get hostname error", e);
		}
		
		String instanceName = System.getProperty("weblogic.Name");
		
		StringBuilder sb = new StringBuilder("image.search.one");
		sb.append("_").append(hostname).append("_").append(instanceName);

		String url = env.getProperty(sb.toString());
		
		// 해당 업무의 모든 이미지 조회를 위해서 URL 변경
		// 프로퍼티로 두지 않고, 코드상에서 URL 변경함
		url += "/all";
		
		response = searchService.searchByMainkey(url, request);
		
		logger.info("response : {}", response.getResultCode());
		
		if (response.getResultCode() == 200) {
			return response.getImageFileDto();
		}
		
		return null;
	}
	
}