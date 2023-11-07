package com.mstore.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mstore.model.Product;
import com.mstore.model.Review;
import com.mstore.model.User;

@Repository
public interface ReviewRepo extends JpaRepository<Review, Long> {

	public List<Review> findByProduct(Product product);
	
	public List<Review> findByUser(User user);
}
