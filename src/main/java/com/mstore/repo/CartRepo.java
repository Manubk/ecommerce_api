package com.mstore.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mstore.model.Cart;

@Repository
public interface CartRepo extends JpaRepository<Cart, Long> {

	@Query(nativeQuery = true ,value="SELECT * from Cart where user_id=:userId")
	public Cart findByUserId(@Param(value = "userId") Long userId);
}
