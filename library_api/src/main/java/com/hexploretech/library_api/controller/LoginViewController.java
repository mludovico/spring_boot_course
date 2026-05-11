package com.hexploretech.library_api.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginViewController {

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@ResponseBody
	@GetMapping
	public String homePage(Authentication authentication) {
		if (authentication != null && authentication.isAuthenticated()) {
			return "Hello, " + authentication.getName();
		}
		return "redirect:/login";
	}

	@ResponseBody
	@GetMapping("/authorized")
	public String authorized(@RequestParam("code") String code) {
		return "Authorization code: " + code;
	}
}
