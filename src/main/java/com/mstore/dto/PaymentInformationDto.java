package com.mstore.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PaymentInformationDto {

	private Long id;
	
	private String paymentType;
	
	private String cardHolderName;
	
	private String cardNumber;
	
	private LocalDate expirationDate;
	
	private String cvv;
	
	private String upi;
}
