package com.mstore.service;

import com.mstore.dto.CartItemDto;
import com.mstore.model.Cart;
import com.mstore.model.CartItem;
import com.mstore.model.Product;
import com.mstore.response.GeneralResponse;

public interface CartItemService {

	public CartItem createCartItem(CartItem cartItem);
	
	public CartItem updateCartItem(CartItem cartItem);
	
	public CartItem isCartItemExist(Cart cart , Product product , String size , Long userId);
	
	public GeneralResponse removeCartItemById(Long cartItemId);
	
	public CartItem findCartItemById(Long cartItemId);
}
