package com.mobileleader.edoc.monitoring.config.security;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

import ssc.fundamental.SscStartUp;
import ssc.service.session.SscHttpFilter;
import ssc.service.session.SscListener;
import ssc.service.session.SscServlet;

public class WebSecurityInitializer extends AbstractSecurityWebApplicationInitializer {

	@Override
	protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
		
		// 스프링 시큐리티 필터 체인 설정 전에 세션 클러스터링 설정이 들어가야함.
		this.addSessionClustering(servletContext);
		
		super.beforeSpringSecurityFilterChain(servletContext);
	}
    
	
	/**
	 * 세션 클러스터링을 위한 Filter, Servlet, Listener 등록
	 * @param servletContext
	 */
	private void addSessionClustering(ServletContext servletContext) {
    	
    	// Filter
    	FilterRegistration.Dynamic filterReg = servletContext.addFilter("SscFilter", new SscHttpFilter());
    	filterReg.addMappingForUrlPatterns(null, false, "/*");
    	
    	// Servlet
    	ServletRegistration.Dynamic registrationBean = servletContext.addServlet("SscServlet", new SscServlet());
    	
    	registrationBean.addMapping("/SscServlet");
    	
        Map<String, String> initParameters = new HashMap<>();
        initParameters.put("enabledForExtensions", "true");
        
        registrationBean.setInitParameters(initParameters);
        registrationBean.setLoadOnStartup(1);
        
        // Listener
        servletContext.addListener(new SscListener());
        servletContext.addListener(new SscStartUp());
    }
	
}
