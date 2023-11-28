package com.mstore.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PAYMENT_INFORMATION")
public class PayMentInformation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	// For Card
	@Column(name = "PAYMENT_TYPE")
	private String paymentType;
	
	@Column(name = "CARDHOLDER_NAME")
	private String cardHolderName;
	
	@Column(name = "CARD_NUMBER")
	private String cardNumber;
	
	@Column(name = "EXPIRATION_DATE")
	private LocalDate expirationDate;
	
	@Column(name = "CVV")
	private String cvv;
	
	//for upi
	@Column(name = "UPI")
	private String upi;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private User user;
	
	@CreationTimestamp
	private LocalDateTime createdDate;
	
	@UpdateTimestamp
	private LocalDateTime updatedDate;
	
}
