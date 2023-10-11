package com.mstore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class ExceptionManager {

	@ExceptionHandler(value = UserException.class)
	public ResponseEntity<ExceptionModel> handleUserException(){
	
		ExceptionModel exceptionModel = new ExceptionModel("ERROR", HttpStatus.BAD_GATEWAY);
		
		return new ResponseEntity<ExceptionModel>(exceptionModel,exceptionModel.getStatus());
	}
}
