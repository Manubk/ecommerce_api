package com.mstore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
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
	
	@ExceptionHandler(value = AddressNotFoundException.class)
	public ResponseEntity<ExceptionModel> handleAddressNotFoundException(){
		ExceptionModel exceptionModel  = new ExceptionModel("Address Not Found ", HttpStatus.BAD_REQUEST);
		
		return new ResponseEntity<ExceptionModel>(exceptionModel,exceptionModel.getStatus());
	}
	
	@ExceptionHandler(value = ProductNotFoundException.class)
	public ResponseEntity<ExceptionModel> productNotFoundException(){
		ExceptionModel exceptionModel = new ExceptionModel("Product Not Found ", HttpStatus.BAD_REQUEST);
		
		return new ResponseEntity<ExceptionModel>(exceptionModel,exceptionModel.getStatus());
	}
	
	@ExceptionHandler(value = EmailNotAllowedException.class)
	public ResponseEntity<ExceptionModel> emailNotAllowedException(){
		ExceptionModel exceptionModel = new ExceptionModel("Entered Email is Not Allowed Kindly Use Different Email", HttpStatus.BAD_REQUEST);
		return new ResponseEntity<ExceptionModel>(exceptionModel,exceptionModel.getStatus());
	}
	
	@ExceptionHandler(value =  BadCredentialsException.class)
	public ResponseEntity<ExceptionModel> badCredentialsException(){
		ExceptionModel exceptionModel = new ExceptionModel("Credentials Do not match please try again", HttpStatus.UNAUTHORIZED);
	   return new ResponseEntity<ExceptionModel>(exceptionModel,exceptionModel.getStatus());
	}
	
	@ExceptionHandler(value = DisabledException.class)
	public ResponseEntity<ExceptionModel> userDeactivatedException(){
		ExceptionModel exceptionModel = new ExceptionModel("User Deactivated", HttpStatus.FORBIDDEN);
		return new ResponseEntity<ExceptionModel>(exceptionModel,exceptionModel.getStatus());
	}
}
