package com.mstore.model;

import java.time.LocalDateTime;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mstore.dto.ProductDto;
import com.mstore.util.ApplicationUtils;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "TITLE")
	private  String title;

	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "PRICE")
	private double price;
	
	@Column(name = "DISCOUNTED_PRICE")
	private double discountedPrice;
	
	@Column(name = "QUANTITY")
	private Integer quantity;
	
	@Column(name = "BRAND")
	private String brand;
	
	@Column(name = "COLOUR")
	private String colour;
	
	@Embedded
	@ElementCollection(fetch = FetchType.EAGER)
	@Column(name = "SIZES")
	private Set<Size> sizes;
	
	@Column(name = "IMAGE_URL")
	private String imageUrl;	
	
	@Column(name = "NUMBER_OF_RATING")
	private Integer numOfRatings=0;
	
	@Column(name = "RATINGS")
	private float ratings = 0f;
	
	@ManyToOne
	@JoinColumn(name = "CATEGORY_ID")
    private Category category;
	
	@CreationTimestamp
	@Column(name = "CREATED_AT")
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	@Column(name = "UPDATED_AT")
	private LocalDateTime updatedAt;
	
	@JsonIgnore
	@CreatedBy
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATED_BY")
	private User createdBy;
	
	private int getDiscounterPercent() {
		return (int)(((this.price - this.discountedPrice)/this.price)*100);
	}
	
	public Product copyProperty(ProductDto productDto) {
		
		if(productDto.getId() != null)
			this.id = productDto.getId();
		
		if(ApplicationUtils.isNotNullOrEmpty(productDto.getTitle()))
			this.title = productDto.getTitle();
		
		if(ApplicationUtils.isNotNullOrEmpty(productDto.getDescription()))
			this.description = productDto.getDescription();
		
		if(ApplicationUtils.isNotNullOrEmpty(productDto.getBrand()))
			this.brand = productDto.getBrand();
		
		if(ApplicationUtils.isNotNullOrEmpty(productDto.getColour()))
			this.description = productDto.getColour();
		
		if(productDto.getPrice() != null)
			this.price = productDto.getPrice();
		
		if(productDto.getDiscountedPrice() != null)
			this.discountedPrice = productDto.getDiscountedPrice();
		
		if(!productDto.getSizes().isEmpty()) {
		 	Integer totalQuantity = 0;
			for(Size size : productDto.getSizes()) {
				totalQuantity += size.getQuantity();
			}
			
			this.quantity = totalQuantity;
			this.setSizes(productDto.getSizes());
		}
		
		if(productDto.getColour() != null)
			this.colour = productDto.getColour();
		
		if(ApplicationUtils.isNotNullOrEmpty(productDto.getImageUrl()))
			this.imageUrl = productDto.getImageUrl();
	
		return this;
	}
	
}
