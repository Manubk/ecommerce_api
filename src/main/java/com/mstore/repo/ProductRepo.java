package com.mstore.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mstore.model.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long>{
	
	

}
