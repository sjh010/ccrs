package com.mobileleader.edoc.monitoring.controller.login;

import java.util.Map;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mobileleader.edoc.monitoring.common.form.PasswordModifyForm;
import com.mobileleader.edoc.monitoring.service.LoginService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LoginRestController {
	
    private final LoginService loginService;
    
	@PostMapping(value="/password/modify/process")
	public Map<String, Object> statisticsProcList(@ModelAttribute("passwordModifyForm") PasswordModifyForm passwordModifyForm) {
		return loginService.modifyPassword(passwordModifyForm);	
	}
}
