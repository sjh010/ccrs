package com.mobileleader.edoc.monitoring.controller.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	
	@RequestMapping(value="/")
	public String main() {
		return "forward:/edoc/proc/monitoring";
	}
}
