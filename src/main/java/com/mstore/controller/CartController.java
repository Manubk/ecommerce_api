package com.mstore.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mstore.dto.CartItemDto;
import com.mstore.exception.ProductException;
import com.mstore.model.Cart;
import com.mstore.response.GeneralResponse;
import com.mstore.service.CartService;
import com.mstore.service.UserService;

@RestController
@RequestMapping("/api")
public class CartController {

	@Autowired
	private CartService cartService;
	
	Logger log = LoggerFactory.getLogger(CartController.class);
	
	@GetMapping("/cart/user/{userId}")
	public ResponseEntity<Cart> findCartByUser(@PathVariable Long userId){
		log.info("Finding Cart By User id="+userId);
		
		Cart cart = cartService.findCartByUser(userId);
		return new ResponseEntity<Cart>(cart,HttpStatus.OK);
	}
	
	@GetMapping("cart/user")
	public ResponseEntity<Cart> findUserCart(){
		log.info("Finding User Cart ");
		
		Cart userCart = cartService.findUserCart();
		
		return new ResponseEntity<Cart>(userCart,HttpStatus.OK);
	}
	
	
	@PutMapping("/cart/item")
	public ResponseEntity<GeneralResponse> addCartItem(@RequestBody CartItemDto cartItemDto) throws ProductException{
		log.info("Adding item to cart userId="+cartItemDto.getUserId());
		
		GeneralResponse generalResponse = cartService.addCartItem(cartItemDto.getUserId(), cartItemDto);
		
		return new ResponseEntity<GeneralResponse>(generalResponse,HttpStatus.OK);
	}
	
	@PutMapping("/cart/item/{itemId}")
	public ResponseEntity<GeneralResponse> removeItemFromCart(@PathVariable Long itemId){
		log.info("Removing the item from Cart");
		
		GeneralResponse generalResponse = cartService.removeCartItem(itemId);
		
		return new ResponseEntity<GeneralResponse>(generalResponse,HttpStatus.OK);
	}
	
}
