package com.mobileleader.edoc.config;

import java.io.IOException;
import java.util.Properties;

import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(basePackages = {"com.mobileleader"}, 
				excludeFilters = {@Filter(type = FilterType.ANNOTATION, value = {EnableWebMvc.class, Controller.class, RestController.class, ControllerAdvice.class})})
@Import(value = {DataSourceConfig.class})
public class SpringRootConfig {

	@Autowired
	private Environment env;
	
	@Bean(name = "dbProperty")
	public Properties dbProperty() throws IOException {
		return PropertiesLoaderUtils.loadProperties(new ClassPathResource("properties/" + getProfile() + "/db.properties"));
	}
	
	@Bean(name = "inziProperty")
	public Properties inziProperty() throws IOException {
		Properties inziProperty = PropertiesLoaderUtils.loadProperties(new ClassPathResource("properties/" + getProfile() + "/service.properties"));
		inziProperty.put("server.profile", getProfile());
		return inziProperty;
	}
	
	// windows: 시스템 변수 환경설정 등록 (PC재부팅 후 적용됨) 
	//         name= SPRING_PROFILES_ACTIVE
	//			val= loc	        
	// linux: ~/.bashrc 안에 아래 내용 추가 (source ~/.bashrc 명령어 후 적용됨)
	//		  export SPRING_PROFILES_ACTIVE=dev	
	private String getProfile() {
		String profile = "loc";
		
		String[] profiles = env.getActiveProfiles();
		for (String string : profiles) {
			profile = string;
		}
		
		if(profile.startsWith("prd")) {
			profile = "prd";
		}
		return profile;
	}
	
	@Bean(name="validator")
	public Validator validator() {
		LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
		return validatorFactoryBean;
	} 
	
}
