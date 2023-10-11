package com.mstore.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Size {

	private String name;
	
	private int quantity;
	
}
