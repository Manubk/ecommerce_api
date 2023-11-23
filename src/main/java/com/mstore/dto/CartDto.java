package com.mstore.dto;

import java.util.ArrayList;
import java.util.List;

import com.mstore.model.CartItem;

import lombok.Data;

@Data
public class CartDto {

	private Long id;
	
	private Long user_id;
	
	private List<CartItem> cartItems = new ArrayList<CartItem>();
	
	private double totalPrice;
	
	private int totalItem;
	
	private double totalDiscountedPrice;
	
	private int discount;
	
}
