package com.mobileleader.edoc.monitoring.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.access.AccessDeniedHandler;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    
    @SuppressWarnings("unused")
    private static Logger logger = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);
    
    private String defaultMainRedirectUrl = "/edoc/proc/monitoring";
    private String accessDeniedRedirectUrl = "/common/error/403";

    public String getDefaultMainRedirectUrl() {
        return defaultMainRedirectUrl;
    }

    public void setDefaultMainRedirectUrl(String defaultMainRedirectUrl) {
        this.defaultMainRedirectUrl = defaultMainRedirectUrl;
    }

    public String getAccessDeniedRedirectUrl() {
        return accessDeniedRedirectUrl;
    }

    public void setAccessDeniedRedirectUrl(String accessDeniedRedirectUrl) {
        this.accessDeniedRedirectUrl = accessDeniedRedirectUrl;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException, ServletException {
        RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        String targetUrl = getAccessDeniedRedirectUrl();
        if (auth != null && !(auth instanceof AnonymousAuthenticationToken)) {
            targetUrl = getDefaultMainRedirectUrl();
        } 
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

}
