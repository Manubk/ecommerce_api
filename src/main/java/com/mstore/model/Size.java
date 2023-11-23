package com.mstore.model;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Size implements Serializable {

	private String name;
	
	private int quantity;
	
}
