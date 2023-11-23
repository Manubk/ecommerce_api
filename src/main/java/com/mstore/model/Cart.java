package com.mstore.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	
	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID",nullable = false)
	private User user;

	@JsonIgnore
	@OneToMany(mappedBy = "cart",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private List<CartItem> cartItems = new ArrayList<CartItem>();
	
	@Column(name = "TOTAL_PRICE")
	private double totalPrice;
	
	@Column(name = "TOTAL_ITEM")
	private int totalItem;
	
	@Column(name = "TOTAL_DISCOUNTED_PRICE")
	private double totalDiscountedPrice;
	
	@Column(name = "DISCOUNT")
	private int discount;
	
}
