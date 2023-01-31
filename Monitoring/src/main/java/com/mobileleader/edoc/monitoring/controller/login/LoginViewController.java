package com.mobileleader.edoc.monitoring.controller.login;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.mobileleader.edoc.monitoring.common.annotation.ViewController;

@ViewController
public class LoginViewController {

    @RequestMapping(value="/login")
    public ModelAndView login(@RequestParam(required = false, defaultValue = "false") String error, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        
        mav.addObject("error", error);      
        if (error != null && error.equals("true")) {
            mav.addObject("userName", request.getAttribute("userName"));    
            mav.addObject("userPassword", request.getAttribute("userPassword"));    
            mav.addObject("securityExceptionMassage", request.getAttribute("securityExceptionMassage"));
        }
        mav.setViewName("/login/loginForm");
        return mav;
    }
    
    @GetMapping(value="/logout")
    public void logoutPage (HttpServletRequest request, HttpServletResponse response) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {    
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
        redirectStrategy.sendRedirect(request, response, "/login");
    }
    
    @GetMapping(value="/password/modify")
    public String password() {
        return "/login/passwordModifyPopup";
    }
    
    /**
     * SSO Authentication index: Customizing
     */
    @GetMapping(value = "/sso")
    public String sso() {
        return "/sso/ssoCheck";
    }
    
    /**
     * SSO System Authentication complete: Customizing
     * 
     * @param request
     * @param response
     */
    @PostMapping(value = "/sso/proc")
    public void ssoComplete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
    }
}
