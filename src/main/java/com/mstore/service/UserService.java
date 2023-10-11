package com.mstore.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mstore.dto.UserDto;
import com.mstore.exception.UserException;
import com.mstore.model.User;
import com.mstore.response.AuthResponse;

@Service
public interface UserService {
	
	public void createUser(UserDto userDto) throws UserException;
	
	public UserDto findUserById(Long id) throws UserException;
	
	public UserDto findUserByJwt(String jwt) throws UserException;
	
	public AuthResponse updateUser(UserDto userDto) throws UserException;
	
	public UserDto findUserByEmail(String email) throws UserException;
	
	public List<UserDto> findAllUsers();
}
