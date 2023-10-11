package com.mstore.dto;

import lombok.Data;

@Data
public class CartItemDto {
 
	private Long productId;
	
	private String size;
	
	private int quantity;
	
	private double price;
	
	
}
