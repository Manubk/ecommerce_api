package com.mstore.dto;

import lombok.Data;

@Data
public class SignUpDto {

	private String email;
	
	private String password;
	
	private String conformPassword;
}
