package com.mstore.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.mstore.constants.ExceptionConstants;
import com.mstore.dto.UserDto;
import com.mstore.exception.UserException;
import com.mstore.model.User;
import com.mstore.repo.UserRepo;
//import com.mstore.repo.UserRepo;
import com.mstore.response.AuthResponse;
import com.mstore.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Override
	public void createUser(UserDto userdto) throws UserException {
		
		//First check the user is exist in database 
		User user = new User();
		BeanUtils.copyProperties(userdto, user);
		
		if(findUserByEmail(user.getEmail()) == null) {
			userRepo.save(user);
		}else {
			throw new UserException("Please Enter Different Email");
		}
		
	}
	
	@Override
	public UserDto findUserById(Long id) throws UserException  {
		
	     Optional<User> userOption = userRepo.findById(id);
	     
	     if(userOption.isPresent()) {
	    	 UserDto userDto = new UserDto();
	    	 BeanUtils.copyProperties(userOption.get(), userDto);
	    	 return userDto;
	     }else {
	    	 throw new UserException("User Not Present :(");
	     }
	
		
	
	}

	@Override
	public UserDto findUserByJwt(String jwt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AuthResponse updateUser(UserDto userDto) throws UserException {
		User user = new User();
		
		BeanUtils.copyProperties(userDto, user);
		
		
		return null;
	}

	@Override
	public UserDto findUserByEmail(String email) throws UserException {
		
		User user = userRepo.findByEmail(email);
		UserDto userDto ;
		
		if(user != null) {
			userDto = new UserDto();
			BeanUtils.copyProperties(user, userDto);
			return userDto;
		}
		
		return null ;
	}

	@Override
	public List<UserDto> findAllUsers() {
		
		List<User> users = userRepo.findAll();
		List<UserDto> userDtos = new ArrayList<UserDto>();
		
		
		for(User user : users) {
			UserDto userDto = new UserDto();
			BeanUtils.copyProperties(user, userDto);
			userDtos.add(userDto);
		}
		
		return userDtos;
	}

	

	
}
