package com.mstore.model;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@OneToOne
	@Column(name = "PAYMENT_ID")
	private PayMentInformation paymentInformation;
	
	@Column(name = "SHIPPING_ADDRESS")
	private Address shippingAddress;
	
	private List<OrderItem> orderItems;
	
	@Column(name = "TOTAL_PRICE")
	private double totalPrice;
	
	@Column(name = "TOTAL_DISCOUNTED_PRICE")
	private double totalDiscountedPrice;
	
	@Column(name = "STATUS")
	private String Status;     //ORDERED , PLACED , SHIPPED , DELIVERED
	
	@CreationTimestamp
	@Column(name = "CREATED_AT")
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	@Column(name = "UPDATED_AT")
	private LocalDateTime updatedAt;
}
