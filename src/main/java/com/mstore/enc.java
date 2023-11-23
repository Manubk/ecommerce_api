package com.mstore;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class enc {
	
	public static void main(String... args) {
		System.out.println(new BCryptPasswordEncoder().encode("123"));
	}
}
