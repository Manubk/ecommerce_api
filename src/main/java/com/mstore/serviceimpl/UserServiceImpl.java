package com.mstore.serviceimpl;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;

import com.mstore.constants.ApplicationConstants;
import com.mstore.constants.ExceptionConstants;
import com.mstore.dto.LoginDto;
import com.mstore.dto.SignUpDto;
import com.mstore.dto.UserDto;
import com.mstore.exception.EmailNotAllowedException;
import com.mstore.exception.UserException;
import com.mstore.exception.UserNotFoundException;
import com.mstore.model.Cart;
import com.mstore.model.User;
import com.mstore.repo.UserRepo;
//import com.mstore.repo.UserRepo;
import com.mstore.response.AuthResponse;
import com.mstore.response.GeneralResponse;
import com.mstore.security.CustomeUser;
import com.mstore.service.CartService;
import com.mstore.service.UserService;
import com.mstore.util.ApplicationUtils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
		
	Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public GeneralResponse createUser(SignUpDto signUpDto) throws UserException {

		log.info("Creating a new User email="+signUpDto.getEmail());
		
		// Checking is the two passwords are matching
		if(!signUpDto.getPassword().equals(signUpDto.getConformPassword()))
			throw new BadCredentialsException("Password DoesNot Match  ;(");
		
		// First check the user is exist in database
		User user = new User();
		BeanUtils.copyProperties(signUpDto, user);

		if (findUserByEmail(user.getEmail()) == null) {
			
			//Before saving encrypt the password
			//Setting Default Role as User
			user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
			user.setRole(ApplicationConstants.USER);
			User savedUser = userRepo.save(user);
			
			// When user is created a default cart should be created
			Cart savedCard = cartService.createCart(savedUser);
			
			return new GeneralResponse.GeneralResposeBuilder().setIsSuccess(true)
					.setMessage("User Created Successful ;) "+savedUser.getId()).build();
			
		} else {
			throw new EmailNotAllowedException("Kindly Change the Email ;(");
		}

	}

	@Override
	public UserDto findUserById(Long id) throws UserException {
		log.info("Finding User By Id "+id);
		
		Optional<User> userOption = userRepo.findById(id);

		if (userOption.isPresent()) {
			UserDto userDto = new UserDto();
			BeanUtils.copyProperties(userOption.get(), userDto);
			return userDto;
		} else {
			log.error("User Not Present id="+id);
			throw new UserException("User Not Present :(");
		}

	}

	@Override
	public UserDto findUserByJwt(String jwt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GeneralResponse updateUser(UserDto userDto) throws UserException {
		User logedInUser = ApplicationUtils.getLogedInUser();
		log.info("Updating the user id="+logedInUser.getEmail());
		
		logedInUser.copyProperty(userDto); 
		userRepo.save(logedInUser);

		return new GeneralResponse.GeneralResposeBuilder().setIsSuccess(true)
				.setMessage("User Details Updated ;)").build();
	}

	@Override
	public UserDto findUserByEmail(String email) throws UserException {
		log.info("Getting user by email="+email);
		
		User user = userRepo.findByEmail(email);
		UserDto userDto;

		if (user != null) {
			userDto = new UserDto();
			BeanUtils.copyProperties(user, userDto);
			return userDto;
		}

		return null;
	}

	@Override
	public List<UserDto> findAllUsers() {
		log.info("Finding all users ");
		
		List<User> users = userRepo.findAll();
		List<UserDto> userDtos = new ArrayList<UserDto>();
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		

		for (User user : users) {
			UserDto userDto = new UserDto();
			BeanUtils.copyProperties(user, userDto);
			userDtos.add(userDto);
		}

		return userDtos;
	}

	@Override
	public GeneralResponse deleteUserById(Long userId) throws UserException {
		log.info("Deleting User By id="+userId);
		
		try{
		userRepo.deleteById(userId);
		return new GeneralResponse.GeneralResposeBuilder().setIsSuccess(true)
				.setMessage("User Deleted Successfull ;)").build();
		
		}catch(Exception e) {
			log.error("Exception in User Deletiong id="+userId+"  msg="+e.getMessage());
			
			throw new UserException("USR NOT FOUND :(");
		}
	}

	@Override
	public User findUserByEmailAndPass(LoginDto loginDto) throws UserException {
		log.info("Finding user by Email="+loginDto.getEmail()+"and pass");
		return null;
	}

	@Override
	public GeneralResponse login(LoginDto loginDto) throws UserNotFoundException {
		log.info("Login User email="+loginDto.getEmail());
		
		Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
		
		if(authenticate.isAuthenticated()) {
			
		//TODO jwt tocken should be created after successfull login 
		
			return new GeneralResponse.GeneralResposeBuilder().setIsSuccess(true).setMessage("Login in Successsfull")
					.build();
		}
		
		return new GeneralResponse.GeneralResposeBuilder().setIsSuccess(false).setMessage("Login in Failes :(").build();
	}

	@Override
	public GeneralResponse deactivateUser() {
		User logedInUser = ApplicationUtils.getLogedInUser();
		log.info("Deactivating Account "+logedInUser.getEmail());	
		
		if(logedInUser.getActivated())
			logedInUser.setActivated(false);
		else
			logedInUser.setActivated(true);
		
		//Now Updating the user
		userRepo.save(logedInUser);
		
		return new GeneralResponse.GeneralResposeBuilder().setIsSuccess(true).setMessage("User Deactivated See you soon;)").build();
				
	}

	@Override
	public UserDto findLogedInUser() {
		log.info("Finding Loged In User");
		
		User logedInUser = ApplicationUtils.getLogedInUser();
		UserDto userDto = new UserDto();
		
		BeanUtils.copyProperties(logedInUser, userDto);
		return userDto;
	}

}
