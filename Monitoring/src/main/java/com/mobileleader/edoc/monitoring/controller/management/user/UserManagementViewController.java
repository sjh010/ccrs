package com.mobileleader.edoc.monitoring.controller.management.user;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobileleader.edoc.monitoring.common.annotation.ViewController;
import com.mobileleader.edoc.monitoring.common.form.UserListForm;
import com.mobileleader.edoc.monitoring.db.dto.PermMgmtDto;
import com.mobileleader.edoc.monitoring.db.dto.UserMgmtDto;
import com.mobileleader.edoc.monitoring.service.PermMgmtService;
import com.mobileleader.edoc.monitoring.service.UserMgmtService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@ViewController
@PropertySource("classpath:properties/ccrs.properties")
@RequestMapping(value="/info/userAdmin")
@RequiredArgsConstructor
@Slf4j
public class UserManagementViewController {

	private final UserMgmtService userMgmtService;
	
	private final PermMgmtService permMgmtService;
	
	private final Environment env;
	
	@GetMapping
	@Secured("ROLE_C02_ACCS")
	public ModelAndView userAdmin(@ModelAttribute("userListForm") UserListForm userListForm) {
		logger.info("/info/userAdmin");
		
		List<PermMgmtDto> permList = permMgmtService.getPermList();
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("permList", permList);
		mav.setViewName("/info/userAdmin");
		return mav;
	}
	
	/**
	 * 사용자 관리 추가 팝업
	 * 
	 * @param empNo
	 * @return
	 */
	@GetMapping(value="/edit")
	@Secured({"ROLE_C02_EDIT", "ROLE_C02_REG"})
	public ModelAndView userEdit(@RequestParam(value="empNo", required = false) String empNo) {
		
		ModelAndView mav = new ModelAndView();
		
		// 신용회복위원회 부서 목록 추가
		mav.addObject("departments", searchCcrsDepartmentList());
		
		List<PermMgmtDto> permList = permMgmtService.getPermList();
		
		mav.addObject("permList", permList);
		
		if(empNo == null || empNo.isEmpty()) {
			mav.addObject("mode", "add");
			mav.addObject("userInfo", new UserMgmtDto());
		} else {
			mav.addObject("mode", "edit");
			
			UserMgmtDto userDto = userMgmtService.selectByEmpNo(empNo);
			mav.addObject("userInfo", userDto);
		}
		
		mav.setViewName("/info/userAddPopup");
		
		return mav;		
	}	
	
	/**
	 * 신용회복위원회 부서 목록 조회.
	 */
	private List<HashMap<String, String>> searchCcrsDepartmentList() {
		
		String hostname = "";
		
		try {
			 hostname = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			logger.error("get hostname error", e);
		}
		
		String url = env.getProperty("ccrs.department.search.url" + "_" + hostname);
		
		HttpGet httpGet = new HttpGet(url);
		
		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			
			String response = EntityUtils.toString(httpClient.execute(httpGet).getEntity());
			
			ObjectMapper mapper = new ObjectMapper();
			
			List<HashMap<String, String>> list = mapper.readValue(response, new TypeReference<List<HashMap<String, String>>>(){});
			
			return list;
		} catch (IOException e) {
			logger.error("CCRS Department Search Error : {}", e.getMessage(), e);
		}
		
		return null;
	}
}


 