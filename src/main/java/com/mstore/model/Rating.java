package com.mstore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
public class Rating {
	
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY )
	@Column(name = "ID")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "USER_ID",nullable = false)
	private User user;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "PRODUCT_ID",nullable = false)
	private Product product;
	

}
