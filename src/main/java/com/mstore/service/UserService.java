package com.mstore.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mstore.dto.LoginDto;
import com.mstore.dto.SignUpDto;
import com.mstore.dto.UserDto;
import com.mstore.exception.UserException;
import com.mstore.exception.UserNotFoundException;
import com.mstore.model.User;
import com.mstore.response.GeneralResponse;

@Service
public interface UserService {
	
	public GeneralResponse createUser(SignUpDto signUpDto) throws UserException;
	
	public GeneralResponse login(LoginDto loginDto) throws UserNotFoundException;
	
	public UserDto findUserById(Long id) throws UserException;
	
	public UserDto findUserByJwt(String jwt) throws UserException;
	
	public UserDto findLogedInUser();
	
	public GeneralResponse updateUser(UserDto userDto) throws UserException;
	
	public UserDto findUserByEmail(String email) throws UserException;
	
	public List<UserDto> findAllUsers();
	
	public GeneralResponse deleteUserById(Long userId) throws UserException;
	
	public User findUserByEmailAndPass(LoginDto loginDto) throws UserException;
	
	public GeneralResponse deactivateUser();
	
}
