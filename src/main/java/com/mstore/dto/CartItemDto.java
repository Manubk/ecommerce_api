package com.mstore.dto;

import com.mstore.model.Cart;
import com.mstore.model.Product;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class CartItemDto {
 
	private Long productId;
	
	private String size;
	
	private int quantity;
	
	private double price;
	
	private Long cartId;
	
	private Long userId;
	 
}
