package com.mstore.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mstore.exception.CartItemNotFoundException;
import com.mstore.model.Cart;
import com.mstore.model.CartItem;
import com.mstore.model.Product;

import jakarta.transaction.Transactional;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem, Long> {

	@Query("SELECT ci FROM CartItem ci WHERE ci.cart=:cart AND ci.product=:product AND ci.size=:size AND ci.userId=:userId")
	public CartItem isCartItemExist(@Param("cart")Cart cart ,@Param("product")Product product ,@Param("size")String size,@Param("userId")Long userId);

	@Transactional
	@Modifying
	@Query("DELETE FROM CartItem c WHERE id=:id")
	public void deleteItemFromCart(@Param("id")Long id) throws CartItemNotFoundException;
}
