package com.mstore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.Data;

@Data
public class UserException extends RuntimeException{

	public UserException(String msg) {
		super(msg);
	}
	
}
