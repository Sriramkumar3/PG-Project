package com.Project.ElectNotify.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller 
public class LoginController {

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
    
    @GetMapping("/logout")
    public String logoutPage() {
    	return "redirect:/login";
    }

}
