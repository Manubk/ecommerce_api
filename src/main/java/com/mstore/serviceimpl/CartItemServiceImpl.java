package com.mstore.serviceimpl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mstore.dto.CartItemDto;
import com.mstore.exception.CartItemNotFoundException;
import com.mstore.exception.ProductException;
import com.mstore.model.Cart;
import com.mstore.model.CartItem;
import com.mstore.model.Product;
import com.mstore.model.User;
import com.mstore.repo.CartItemRepo;
import com.mstore.repo.CartRepo;
import com.mstore.response.GeneralResponse;
import com.mstore.service.CartItemService;
import com.mstore.service.CartService;

public class CartItemServiceImpl implements CartItemService{

	@Autowired
	private CartItemRepo cartItemRepo;	
	
	Logger log = LoggerFactory.getLogger(CartItemServiceImpl.class);
	
	@Override
	public CartItem createCartItem(CartItem cartItem) {
		log.info("Creating a CartItem ");
		
		cartItem.setPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity());
//		cartItem.setDiscountedPrice(cartItem.getProduct().getDiscontedPrice() * cartItem.getQuantity());
		
		CartItem savedCartItem = cartItemRepo.save(cartItem);
		
		return savedCartItem;
	}
	@Override
	public CartItem updateCartItem(CartItem cartItem) {
		log.info("Updating CartItem id="+cartItem.getId());
		
		Optional<CartItem> cartItemOption = cartItemRepo.findById(cartItem.getId());
		
		if(cartItemOption.isPresent()) {
		 CartItem cartItemFromDb = cartItemOption.get();
		 
		 cartItemFromDb.setPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity());
//		 cartItemFromDb.setDiscountedPrice(cartItem.getProduct().getDiscontedPrice() * cartItem.getQuantity());
		 
		 CartItem savedCartItem = cartItemRepo.save(cartItemFromDb);
		 
		 return savedCartItem;
		}
		
		throw new CartItemNotFoundException("Item Not Found Exception");
	}
	@Override
	public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId) {
		log.info("Checking Is CartItemExist userId="+userId);
		
		CartItem cartItemExist = cartItemRepo.isCartItemExist(cart, product, size, userId);
		
		return cartItemExist;
	}
	@Override
	public GeneralResponse removeCartItemById(Long cartItemId) {
		log.info("Removing CartItem id="+cartItemId);
		
		cartItemRepo.deleteById(cartItemId);
		
		return new GeneralResponse.GeneralResposeBuilder().setIsSuccess(true).setMessage("Deleted SuccessFul ;)").build();
	}
	@Override
	public CartItem findCartItemById(Long cartItemId) {
		log.info("Finding CartItem By id="+cartItemId);
		
		Optional<CartItem> cartItemOption = cartItemRepo.findById(cartItemId);
		
		if(cartItemOption.isEmpty()) {
			log.error("Cart Item Not Found id="+cartItemId);
			throw new CartItemNotFoundException("Cart Item Not Found :(");
		}
		return cartItemOption.get();
	}
	
	

}
