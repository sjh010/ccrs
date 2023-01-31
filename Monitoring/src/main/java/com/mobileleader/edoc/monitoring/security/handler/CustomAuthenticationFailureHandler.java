package com.mobileleader.edoc.monitoring.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.StringUtils;

import com.mobileleader.edoc.monitoring.db.dto.UserMgmtDto;
import com.mobileleader.edoc.monitoring.db.mapper.UserMgmtMapper;
import com.mobileleader.edoc.monitoring.utils.CleanXssUtils;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
	
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(CustomAuthenticationFailureHandler.class);
	
	@Autowired
	private UserMgmtMapper userMgmtMapper;
	
	private String defaultFailureUrl;
	
	public CustomAuthenticationFailureHandler() {
		this.defaultFailureUrl = "/login?error=true";
	}

	public String getDefaultFailureUrl() {
		return defaultFailureUrl;
	}

	public void setDefaultFailureUrl(String defaultFailureUrl) {
		this.defaultFailureUrl = defaultFailureUrl;
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		String userName = request.getParameter("empNo");
		if (StringUtils.isEmpty(userName)) { 
			userName = "";
		} else {
			userName = CleanXssUtils.cleanXSS(userName);
		}
		
		String userPassword = request.getParameter("password");
		
		if (StringUtils.isEmpty(userPassword)) {
			userPassword = "";
		} else {
			userPassword = CleanXssUtils.cleanXSS(userPassword);
		}
		
		UserMgmtDto user = userMgmtMapper.selectByEmpNo(userName);
		
		int passwordErrorCount = user.getPasswordErrorCount();
		
		request.setAttribute("userName", userName);
		request.setAttribute("userPassword", userPassword);
		request.setAttribute("passwordErrorCount", passwordErrorCount);
		request.setAttribute("securityExceptionMassage", exception.getMessage());
		request.getRequestDispatcher(defaultFailureUrl).forward(request, response);
	}

}
