package com.mstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mstore.dto.UserDto;
import com.mstore.exception.UserException;
import com.mstore.model.User;
import com.mstore.response.AuthResponse;
import com.mstore.service.UserService;

@RestController
@RequestMapping("api/")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("user/{userId}")
	public ResponseEntity<UserDto> findUserById(@PathVariable Long userId) throws UserException{
	
		UserDto user = userService.findUserById(userId);
	
		return new ResponseEntity<UserDto>(user,HttpStatus.OK);
		
	}
	
	@GetMapping("users")
	public ResponseEntity<List<UserDto>> findAllUsers(){
		
		List<UserDto> userDtos = userService.findAllUsers();
		
		return new ResponseEntity<List<UserDto>>(userDtos,HttpStatus.OK);
	}
	
	@PostMapping("user")
	public ResponseEntity<AuthResponse> saveUser(@RequestBody UserDto userDto) throws UserException{
		
		AuthResponse authResponse = new AuthResponse();
		
		return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.OK);
	}

}
