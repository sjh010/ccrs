package com.mobileleader.edoc.monitoring.config;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@Configuration
@ComponentScan(basePackages = "com.mobileleader", excludeFilters = {
        @Filter(type=FilterType.REGEX, pattern="com.mobileleader.webform.eform.*"),
        @Filter(type=FilterType.REGEX, pattern="com.mobileleader.webform.sync.*")
})
public class RootConfiguration {

    @Bean(name = "service")
    public PropertiesFactoryBean properties() {
        PropertiesFactoryBean bean = new PropertiesFactoryBean();
        bean.setLocation(new ClassPathResource("properties/service.properties"));
        return bean;
    }
    
    /**
     * TODO 위치 변경 해야함
     * passwordEncoder는 security config에 있어야 될거 같지만
     * injection 하는 과정(순서) 때문에 임시로 여기서 bean 생성함
     * @author asyu (2019-05-15)
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor(); 
    }
}
