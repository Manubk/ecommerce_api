package com.mstore.exception;

public class PaymentInformationNotFoundException extends RuntimeException{
	
	PaymentInformationNotFoundException(String msg) {
		super(msg);
	}
}
