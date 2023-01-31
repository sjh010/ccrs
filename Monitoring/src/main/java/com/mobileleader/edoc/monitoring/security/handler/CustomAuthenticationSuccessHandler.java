package com.mobileleader.edoc.monitoring.security.handler;

import java.io.IOException;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	
	private String defaultSuccessUrl;

	public String getDefaultSuccessUrl() {
		return defaultSuccessUrl;
	}

	public void setDefaultSuccessUrl(String defaultSuccessUrl) {
		this.defaultSuccessUrl = defaultSuccessUrl;
	}
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean authorized = AuthorityUtils.authorityListToSet(authorities).contains("ROLE_A00_ACCS");
        if(authorized) {
            redirectStrategy.sendRedirect(request, response, getDefaultSuccessUrl());
        } else {
            redirectStrategy.sendRedirect(request, response, "/edoc/proc/monitoring");
        }
	}
}
