package com.mobileleader.edoc.monitoring.controller.edoc.imageview;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.core.env.Environment;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mobileleader.edoc.monitoring.common.annotation.ViewController;
import com.mobileleader.edoc.monitoring.common.model.request.ImageSearchReqeust;
import com.mobileleader.edoc.monitoring.common.type.GroupCode;
import com.mobileleader.edoc.monitoring.config.storage.EdocFileStorage;
import com.mobileleader.edoc.monitoring.service.InfoCodeMgmtService;
import com.mobileleader.edoc.monitoring.service.image.ImageServerService;
import com.mobileleader.image.client.service.SearchService;
import com.mobileleader.image.db.dto.ImageStoreDto;
import com.mobileleader.image.server.model.request.SearchRequest;
import com.mobileleader.image.server.model.response.SearchResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@ViewController
@RequestMapping(value="/edoc/view")
@RequiredArgsConstructor
@Slf4j
public class EdocImageViewController {
	
	private final Environment env;
	
	private final InfoCodeMgmtService infoCodeMgmtService;
	
	private final ImageServerService imageServerService;
	
	@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
	@Secured("ROLE_D01_ACCS")
	public ModelAndView main(@ModelAttribute("searchRequest") ImageSearchReqeust searchRequest) {
		logger.info("/edoc/view");
		logger.info("Search Image Store...");
		
		logger.info(searchRequest.toString());
		
		ModelAndView mav = new ModelAndView();
		
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
		String today = formatter.format(date);
		
		String startDate = searchRequest.getStartDate();
		String endDate = searchRequest.getEndDate();

		searchRequest.setStartDate((StringUtils.isEmpty(startDate)) ? today : startDate);
		searchRequest.setEndDate((StringUtils.isEmpty(endDate)) ? today : endDate);

		mav.addObject("procsTypeCodeList", infoCodeMgmtService.getGroupCodeList(GroupCode.PROCESS_TYPE_CODE.getCode()));
		mav.addObject("workTypeCodeList", infoCodeMgmtService.getGroupCodeList(GroupCode.WORK_TYPE_CODE.getCode()));
		mav.addObject("departmentList", imageServerService.getDepartmentList());
		mav.setViewName("/edoc/view/viewList");
		
		return mav;
	}
	
	/**
	 * 로컬 스토리지 뷰어 화면
	 * @param elecDocGroupInexNo
	 * @param viewSeq
	 * @return
	 */
	@RequestMapping(value="/sform/{mainKey}")
	@Secured("ROLE_D01_DTAL")
	public ModelAndView viewSform(@PathVariable("mainKey") String mainKey) {
		logger.info("/edoc/view/sform/{mainKey}");
		logger.info("mainKey : {}", mainKey);
		
		ModelAndView mav = new ModelAndView();
		
		SearchRequest request = new SearchRequest().setImageStoreDto(new ImageStoreDto().setMainKey(mainKey));
		
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
		
		logger.info("URL : {}", url);
		
		response = searchService.searchByMainkey(url, request);
		
		logger.info("response : {}", response.getResultCode());
		
		if (response.getResultCode() == 200) {
			mav.addObject("imageStore", response.getImageStoreDto());
			mav.addObject("files", response.getImageFileDto());
		}
		
		mav.addObject("workTypeCodeList", infoCodeMgmtService.getGroupCodeList(GroupCode.WORK_TYPE_CODE.getCode()));
		mav.addObject("workCodeList", infoCodeMgmtService.getGroupCodeList(GroupCode.WORK_CODE.getCode()));
		mav.setViewName("/edoc/view/viewSformPopup");
		
		return mav;
	}
	
	@RequestMapping(value = "/pdfview/{fileId}")
	public ModelAndView callAdobe(@PathVariable String fileId) {
		logger.info("/edoc/view/pdfview/{fileId}");
		logger.info("fileId : {}", fileId);
		
		ModelAndView mav = new ModelAndView();
		
		EdocFileStorage storage = new EdocFileStorage(env);
		
		File file = storage.getFile(fileId);
		
		mav.addObject("file", file);
		
		mav.setViewName("/edoc/view/pdfViewer");
		
		return mav;
	}

}