package com.mstore.model;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "CATEGORY")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	//need to add maximum letter 50 and not null
	@Column(name = "NAME")
	private String name;
	
//	@ManyToOne(fetch = FetchType.EAGER)
//	@JoinColumn(name = "PARENT_CATEGORY_ID")
//	private Category parentCategory;
	 
	@Column(name = "LEVEL")
	private Integer level;
}
