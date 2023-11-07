package com.mstore.dto;

import lombok.Data;

@Data
public class ReviewDto {

	private Long id;
	
	private Long productId;
	
	private Long userId;
	
	private String review;
}
