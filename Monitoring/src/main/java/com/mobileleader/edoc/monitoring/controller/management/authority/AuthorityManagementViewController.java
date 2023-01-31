package com.mobileleader.edoc.monitoring.controller.management.authority;

import java.util.List;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.mobileleader.edoc.monitoring.common.annotation.ViewController;
import com.mobileleader.edoc.monitoring.db.dto.PermMgmtDto;
import com.mobileleader.edoc.monitoring.service.PermMgmtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@ViewController
@RequestMapping(value="/info/authAdmin")
@RequiredArgsConstructor
@Slf4j
public class AuthorityManagementViewController {
	
	private final PermMgmtService permMgmtService;
	
	@GetMapping
	@Secured("ROLE_C03_ACCS")
	public ModelAndView authAdmin(@RequestParam(value="permId", required=false, defaultValue="1") int permId) {
		
		logger.info("/info/authAdmin");				
		
		List<PermMgmtDto> permList = permMgmtService.getPermList();
		
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("permList", permList);
		mav.addObject("permId", permId);
		mav.setViewName("/info/authAdmin");
		
		return mav;
	}
	
	@GetMapping(value="/edit")
	@Secured({"ROLE_C03_REG", "ROLE_C03_EDIT", "ROLE_C03_DEL"})
	public ModelAndView editPerm(@RequestParam("permId") int permId) {	
		ModelAndView mav = new ModelAndView();
		
		logger.info("/info/authAdmin/edit] permId : {} ", permId);				
		
		List<PermMgmtDto> permList = permMgmtService.getPermList();
		
		mav.addObject("permList", permList);
		mav.addObject("permId", 1);
		mav.setViewName("/info/authEditPopup");		
		
		return mav;
	}
}
