package com.mstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.mstore.model.User;
import com.mstore.repo.UserRepo;
import com.mstore.security.CustomeUser;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomeUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("Loading User By userName="+username);
		
		User user = userRepo.findByEmail(username);
		if(user != null) {
			return new CustomeUser(user);
		}else {
			throw new UsernameNotFoundException(username+"Not Present");
		}
			
	}

}
