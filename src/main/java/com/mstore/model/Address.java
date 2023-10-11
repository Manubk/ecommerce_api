package com.mstore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "FIRST_NAME")
	private  String firstName;
	
	@Column(name = "LAST_NAME")
	private String lastName;
	
	@Column(name = "STREET_ADDRESS")
	private String streetAddress;
	
	@Column(name = "CITY")
	private String city;
	
	@Column(name = "STATE")
	private String state;
	
	@Column(name = "ZIP_CODE")
	private String zipCode;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private User user;
	
	@Column(name = "MOBILE")
	private String mobile;
	
	
}
