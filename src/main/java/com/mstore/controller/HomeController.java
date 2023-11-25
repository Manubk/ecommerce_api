package com.mstore.controller;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	
	public String home() {
		return "home";
	}
}
