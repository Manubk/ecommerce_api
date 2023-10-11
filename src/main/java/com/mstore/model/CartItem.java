package com.mstore.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class CartItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "CART_ID")
	private Cart cart;
	
	@ManyToOne
	@JoinColumn(name = "PRODUCT_ID")
	private Product product;
	
	@Column(name = "SIZE")
	private String size;
	
	@Column(name = "QUANTITY")
	private int quantity;
	
	@Column(name = "PRICE")
	private double price;
	
	@Column(name = "DISCOUNT_PERCENTAGE")
	private int discountPercentage;
	
	@Column(name = "DISCOUNTED_PRICE")
	private Integer discountedPrice;
	
	@Column(name = "USER_ID")
	private Long userId;
}
