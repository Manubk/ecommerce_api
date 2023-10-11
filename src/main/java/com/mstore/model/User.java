package com.mstore.model;

import java.util.List;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "FIRST_NAME")
	private String firstName;
	
	@Column(name = "LAST_NAME")
	private String lastName;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "ROLE")
	private String role;
	
	@Column(name = "MOBILE")
	private String mobile;
	
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "user" )
	private List<Address> address;
	 
	
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<PayMentInformation> paymentInformation;
	
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	private List<Rating> rating;
	
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	private List<Review> review;
}
