package com.mstore.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mstore.dto.AuthDto;
import com.mstore.dto.LoginDto;
import com.mstore.dto.SignUpDto;
import com.mstore.dto.UserDto;
import com.mstore.response.GeneralResponse;
import com.mstore.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<GeneralResponse> login(@RequestBody LoginDto loginDto){
		log.info("Login into a User email="+loginDto.getEmail());
		
		GeneralResponse generalResponse = userService.login(loginDto);
		return new ResponseEntity<GeneralResponse>(generalResponse,HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/signup")
	public ResponseEntity<GeneralResponse> signUp(@RequestBody SignUpDto signUp){
		log.info("Signing up a New User email="+signUp.getEmail());
		
		GeneralResponse generalResponse = userService.createUser(signUp);
		return new ResponseEntity<GeneralResponse>(generalResponse,HttpStatus.ACCEPTED);
	}
	
	
}
