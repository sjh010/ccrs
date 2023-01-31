package com.mobileleader.edoc.monitoring.controller.management.code;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.mobileleader.edoc.monitoring.common.annotation.ViewController;
import com.mobileleader.edoc.monitoring.common.form.InfoCodeForm;

@ViewController
@RequestMapping(value="/info/codeAdmin")
public class CodeManagementViewController {

	/**
	 * 코드관리 페이지로 이동한다
	 * @param infoCodeForm
	 * @return
	 */
	@GetMapping(value="")
	@Secured("ROLE_C01_ACCS")
	public ModelAndView codeAdmin(@ModelAttribute("infoCodeForm") InfoCodeForm infoCodeForm) throws Exception{
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/info/codeAdmin");
		return mav;
	}
	
}

 