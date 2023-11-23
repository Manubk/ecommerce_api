package com.mstore.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.mstore.model.Product;
import com.mstore.model.User;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long>{
	
public List<Product> findProductByCreatedBy(User createdBy);

}
