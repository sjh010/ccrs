package com.mobileleader.edoc.monitoring.controller.common;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.mobileleader.edoc.monitoring.common.annotation.ViewController;

@ViewController
@RequestMapping("/common")
public class CommonViewController {

    @GetMapping(value="/error") 
    public String error() { 
        return "/common/error/error"; 
    }
    
    @GetMapping(value="/error/403") 
    public String errorDenied() { 
        return "/common/error/403"; 
    }
    
    @GetMapping(value="/error/404") 
    public String error404() { 
        return "/common/error/404"; 
    }
    
    @GetMapping(value="/error/500") 
    public String error500() { 
        return "/common/error/500"; 
    }
}
