package com.mstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mstore.dto.AuthDto;
import com.mstore.dto.LoginDto;
import com.mstore.dto.UserDto;
import com.mstore.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserService userService;
	
	public ResponseEntity<AuthDto> login(@RequestBody LoginDto login){
	
		return null;
	}
	
	public ResponseEntity<AuthDto> signUp(@RequestBody UserDto userDto){
	
		return null;
	}
	
}
