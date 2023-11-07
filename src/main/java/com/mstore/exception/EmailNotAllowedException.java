package com.mstore.exception;

public class EmailNotAllowedException extends RuntimeException {
	
	public EmailNotAllowedException(String msg) {
		super(msg);
	}
}
