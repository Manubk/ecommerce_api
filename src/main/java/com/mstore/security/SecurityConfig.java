package com.mstore.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.mstore.constants.ApplicationConstants;
import com.mstore.service.CustomeUserDetailService;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

	Logger log = LoggerFactory.getLogger(SecurityConfig.class);
	
	@Bean	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		log.info("Security Filter Chain");
		http.csrf().disable()
		.authorizeHttpRequests((authorize) -> authorize
	    .requestMatchers("/api/**").authenticated()
	    .requestMatchers("api/product").hasAnyRole("ADMIN","SELLER")
	    .anyRequest().permitAll())
		.httpBasic(Customizer.withDefaults());
		return http.build();

	}
	
	@Bean
	public AuthenticationManager authenticationManager() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		
		return new ProviderManager(authenticationProvider);
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new CustomeUserDetailService();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
