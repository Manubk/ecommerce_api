package com.mstore.controller;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.PutExchange;

import com.mstore.dto.SignUpDto;
import com.mstore.dto.UserDto;
import com.mstore.exception.UserException;
import com.mstore.model.User;
import com.mstore.response.AuthResponse;
import com.mstore.response.GeneralResponse;
import com.mstore.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("api/")
public class UserController {
	
	@Autowired
	private UserService userService;

	Logger log = LoggerFactory.getLogger(UserController.class);
	
	@GetMapping("user")
	public ResponseEntity<UserDto> findUserById() throws UserException{
		log.info("Finding User ");
		
		UserDto user = userService.findLogedInUser();
	
		return new ResponseEntity<UserDto>(user,HttpStatus.OK);
		
	}
	
	@GetMapping("users")
	public ResponseEntity<List<UserDto>> findAllUsers(){
		log.info("Getting all Users");
		
		List<UserDto> userDtos = userService.findAllUsers();
		
		return new ResponseEntity<List<UserDto>>(userDtos,HttpStatus.OK);
	}
	
	@PostMapping("user")
	public ResponseEntity<GeneralResponse> createUser(@RequestBody SignUpDto  signUpDto) throws UserException{
		log.info("Creating user email="+signUpDto.getEmail());
		
		GeneralResponse generalResponse = userService.createUser(signUpDto);
		
		return new ResponseEntity<GeneralResponse>(generalResponse,HttpStatus.OK);
	}
	
	@PutMapping("/user")
	public ResponseEntity<GeneralResponse> updateUser(@RequestBody UserDto userDto){
		log.info("Updating user email="+userDto.getEmail());
		
		GeneralResponse generalResponse = userService.updateUser(userDto);
		return new ResponseEntity<GeneralResponse>(generalResponse,HttpStatus.OK);
	}
	
	@DeleteMapping("/user/{userId}")
    public ResponseEntity<GeneralResponse> deleteUserById(@PathVariable Long userId){
    	log.info("Deleting user by id="+userId);
		GeneralResponse generalResponse = userService.deleteUserById(userId);
		return new ResponseEntity<GeneralResponse>(generalResponse,HttpStatus.OK);
    	
    }
	
	@PutMapping("/deactivate")
	public ResponseEntity<GeneralResponse> deactivateAccount(){
		log.info("Deactivating the account");
		
		GeneralResponse generalResponse = userService.deactivateUser();
		return new ResponseEntity<GeneralResponse>(generalResponse,HttpStatus.OK);
				
	}

}
