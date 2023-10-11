package com.mstore.response;

import lombok.Data;

@Data
public class AuthResponse {
	
	private String jwtTocken;
	
	private String message;
}
