package com.mstore.service;

import com.mstore.dto.CartDto;
import com.mstore.dto.CartItemDto;
import com.mstore.exception.CartItemNotFoundException;
import com.mstore.exception.CartNotFoundException;
import com.mstore.exception.ProductException;
import com.mstore.model.Cart;
//import com.mstore.model.Cart;
import com.mstore.model.User;
import com.mstore.response.GeneralResponse;

public interface CartService {

	public Cart createCart(User user);
	
	public GeneralResponse addCartItem(Long userId , CartItemDto cartItem) throws ProductException;
	
	public GeneralResponse increaseQuantity(Long itemId,Integer quantity);
	
	public GeneralResponse removeCartItem(Long itemId) ;
	
	public Cart findCartByUser(Long userId) throws CartItemNotFoundException;
	
	public Cart findUserCart() throws CartNotFoundException;
	
	public CartDto findUserDtoCart() throws CartItemNotFoundException;
	
	public GeneralResponse saveForLater(Long itemId);

    public Cart updateCartAfterChange(Cart cart);
}
