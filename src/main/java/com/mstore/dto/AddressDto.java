package com.mstore.dto;

import lombok.Data;

@Data
public class AddressDto {
	
	private Long id;
	
	private Long userId;

	private  String firstName;	

	private String lastName;
	
	private String streetAddress;
		
	private String city;
	
	private String state;
	
	private String zipCode;
	
	private String mobile;

}
