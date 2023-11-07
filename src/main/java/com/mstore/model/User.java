package com.mstore.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mstore.dto.UserDto;

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
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.Value;

@Component
@Entity
@Data
public class User{
	
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
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "ROLE")
	private String role;
	
	@Column(name = "MOBILE")
	private String mobile;
	
	@JsonIgnore
    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL,mappedBy = "user" )
	private List<Address> address = new ArrayList<>();
	 
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<PayMentInformation> paymentInformation;
    
    @OneToOne(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Cart cart;
    
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,mappedBy ="createdBy")
    private List<Product> products;
	
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	private List<Rating> rating;
	
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	private List<Review> review;
    
    //Esentials Fields
    @Column(name = "ACTIVATED" , columnDefinition = "boolean default true")
    private Boolean activated = true;
    
    @CreationTimestamp
    @Column(name = "CREATED_DATE")
    private LocalDateTime createDate;
    
    @UpdateTimestamp
    @Column(name = "UPDATED_DATE")
    private LocalDateTime updatedDate;
    
    
    public User copyProperty(UserDto userDto) {
    	if(userDto.getId() != null)
    		this.id=userDto.getId();
    	
    	if(userDto.getEmail() !=  null)
    		this.email=userDto.getEmail();
    	
    	if(userDto.getFirstName() != null)
    		this.firstName = userDto.getFirstName();
    	
    	if(userDto.getLastName() != null)
    		this.lastName = userDto.getLastName();
    	
    	if(userDto.getMobile() != null)
    		this.mobile = userDto.getMobile();
    	
    	if(userDto.getRole() != null)
    		this.role = userDto.getRole();
    	
    	return this;
    }

}
