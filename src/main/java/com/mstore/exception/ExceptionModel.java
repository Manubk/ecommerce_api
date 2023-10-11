package com.mstore.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionModel {

	private String msg;
	
	private HttpStatus status;
	
}
