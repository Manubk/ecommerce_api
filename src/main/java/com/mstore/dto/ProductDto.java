package com.mstore.dto;

import java.util.Set;

import com.mstore.model.Size;

//import com.mstore.model.Size;

import lombok.Data;

@Data
public class ProductDto {

	private Long id;
	
	private String title;
	
	private String description;
	
	private Double price;
	
	private Double discountedPrice;
	
	private Integer quantity;
	
	private String brand;
	
	private String colour;
	
	private Set<Size> sizes;
	
	private String imageUrl;
	
	private String category;
	
}
