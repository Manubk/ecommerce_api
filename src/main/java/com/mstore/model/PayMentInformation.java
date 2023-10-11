package com.mstore.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name = "PAYMENT_INFORMATION")
public class PayMentInformation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	@Column(name = "CARDHOLDER_NAME")
	private String cardHolderName;
	
	@Column(name = "CARD_NUMBER")
	private String cardNumber;
	
	@Column(name = "EXPIRATION_DATE")
	private LocalDate expirationDate;
	
	@Column(name = "CVV")
	private String cvv;
	
	@ManyToOne()
	@JoinColumn(name = "USER_ID")
	private User user;
	
}
