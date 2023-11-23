package com.mstore.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.mstore.model.OrderItem;

import lombok.Data;

@Data
public class OrderDto {
	private Long id;

	private Long userId;

	private Long addressId;
	
	private Long paymentId;

	private List<OrderItem> orderItems;

	private double totalPrice;

	private double totalDiscountedPrice;

	private String status;

	private LocalDateTime createdAt;
	
}
