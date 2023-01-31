package com.mobileleader.edoc.monitoring.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import com.mobileleader.edoc.monitoring.security.handler.CustomAccessDeniedHandler;
import com.mobileleader.edoc.monitoring.security.handler.CustomAuthenticationFailureHandler;
import com.mobileleader.edoc.monitoring.security.handler.CustomAuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService loginService;
    
    private final AuthenticationProvider customAuthenticationProvider;
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(loginService)
            .and();
            //.authenticationProvider(customAuthenticationProvider);
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js/**")
                      .antMatchers("/webform/**")
                      .antMatchers("/images/**")
                      .antMatchers("/css/**")
                      .antMatchers("/html/**")
                      .antMatchers("/favicon.ico")
                      .antMatchers("/edoc/file/download/log/**")
                      .antMatchers("/system/systemMgmt/restart/**")
                      .antMatchers("/sform/**")
                      .antMatchers("/error/**")
                      .antMatchers("/login");
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .headers().disable()
            .authorizeRequests()
                .antMatchers("/login").permitAll()
                .anyRequest().authenticated()
            .and()
            .formLogin()
                .loginPage("/login").permitAll()
                .loginProcessingUrl("/j_spring_security_check")
                .usernameParameter("empNo")
                .passwordParameter("password")
                .successHandler(customAuthenticationSuccessHandler())
                .failureHandler(customAuthenticationFailureHandler())
                //.defaultSuccessUrl("/dashboard", false)
            .and()
            .authenticationProvider(customAuthenticationProvider)
            .logout()
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/login")
                .logoutUrl("/logout").permitAll()
            .and()    
            .sessionManagement()
                .invalidSessionUrl("/logout")
                .maximumSessions(1)
                .expiredUrl("/logout")
                .and()
            .and()
                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler())
            ;
            
    }
    
    @Bean("authenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        CustomAuthenticationSuccessHandler authenticationSuccessHandler = new CustomAuthenticationSuccessHandler();
        authenticationSuccessHandler.setDefaultSuccessUrl("/dashboard");
        return authenticationSuccessHandler;
    }
    
    @Bean
    public AuthenticationFailureHandler customAuthenticationFailureHandler() {
        CustomAuthenticationFailureHandler authenticationFailureHandler = new CustomAuthenticationFailureHandler();
        authenticationFailureHandler.setDefaultFailureUrl("/login?error=true");
        return authenticationFailureHandler;
    }
    
    @Bean
    public AccessDeniedHandler customAccessDeniedHandler() {
        CustomAccessDeniedHandler accessDeniedHandler = new CustomAccessDeniedHandler();
        accessDeniedHandler.setDefaultMainRedirectUrl("/edoc/proc/monitoring");
        accessDeniedHandler.setAccessDeniedRedirectUrl("/common/error/403");
        return accessDeniedHandler;
    }
}