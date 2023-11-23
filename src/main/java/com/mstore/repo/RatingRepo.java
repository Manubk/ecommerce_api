package com.mstore.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mstore.model.Product;
import com.mstore.model.Rating;

public interface RatingRepo extends JpaRepository<Rating, Long>{

	public List<Rating> findByProduct(Product product);
}
