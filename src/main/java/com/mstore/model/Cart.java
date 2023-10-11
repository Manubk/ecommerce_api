package com.mstore.model;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID",nullable = false)
	private User user;
	
	@OneToMany(mappedBy = "cart",cascade = CascadeType.ALL)
	private Set<CartItem> cartItems;
	
	@Column(name = "TOTAL_PRICE")
	private double totalPrice;
	
	@Column(name = "TOTAL_ITEM")
	private int totalItem;
	
	@Column(name = "TOTAL_DISCOUNTED_PRICE")
	private double totalDiscountedPrice;
	
	@Column(name = "DISCOUNT")
	private int discount;
	
}
