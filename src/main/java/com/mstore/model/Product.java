package com.mstore.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
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
	private Integer price;
	
	@Column(name = "DISCOUNTED_PRICE")
	private Integer discontedPrice;
	
	@Column(name = "QUANTITY")
	private Integer quantity;
	
	@Column(name = "BRAND")
	private String brand;
	
	@Column(name = "COLOUR")
	private String colour;
	
	@Embedded
	@ElementCollection
	@Column(name = "SIZES")
	private Set<Size> sizes;
	
	@Column(name = "IMAGE_URL")
	private String imageUrl;
	
	@OneToMany(mappedBy = "product" ,cascade = CascadeType.ALL)
	private List<Rating> rating;	
	
	@OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
	private List<Review> review;
	
	@Column(name = "NUMBER_OF_RATING")
	private Integer numOfRatings;
	
	@ManyToOne
	@JoinColumn(name = "CATEGORY_ID")
    private Category category;
	
	@CreatedDate
	@Column(name = "CREATED_AT")
	private LocalDateTime createdAt;
	
}
