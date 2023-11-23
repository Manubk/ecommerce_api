package com.mstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mstore.response.GeneralResponse;
import com.mstore.service.CartItemService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/cartitem/")
@Slf4j
public class CartItemController {
	
	@Autowired
	 private CartItemService cartItemService;

	 @DeleteMapping("{itemId}")
	 public ResponseEntity<GeneralResponse> deleteItemFromCart(@PathVariable Long itemId){
		 log.info("Deleting the Item From Cart itemId="+itemId);
		 
		 GeneralResponse generalResponse = cartItemService.removeCartItemById(itemId);
		 
		 return new ResponseEntity<GeneralResponse>(generalResponse,HttpStatus.OK);
		 
	 }
	 
	 
}
