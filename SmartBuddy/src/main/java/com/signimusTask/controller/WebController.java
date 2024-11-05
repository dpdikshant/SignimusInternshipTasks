package com.signimusTask.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

	 @GetMapping("/dashboard")
	    public String showDashboard() {
	        return "dashboard"; // This should match the name of your HTML file without the .html extension
	    }
}
