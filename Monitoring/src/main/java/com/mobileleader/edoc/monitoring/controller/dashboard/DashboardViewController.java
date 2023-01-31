package com.mobileleader.edoc.monitoring.controller.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mobileleader.edoc.monitoring.common.annotation.ViewController;
import com.mobileleader.edoc.monitoring.common.type.GroupCode;
import com.mobileleader.edoc.monitoring.service.InfoCodeMgmtService;

@ViewController
@RequestMapping("/dashboard")
public class DashboardViewController {

	@Autowired
	private InfoCodeMgmtService infoCodeMgmtService;
	
    @GetMapping
    @Secured("ROLE_A00_ACCS")
    public ModelAndView getDashboardPage() {
    	ModelAndView mav = new ModelAndView();
    	mav.addObject("insourceTitleList" , infoCodeMgmtService.getGroupCodeList(GroupCode.WORK_TYPE_CODE.getCode()));
    	mav.setViewName("/dashboard/dashboard");
        return mav;
    }
}