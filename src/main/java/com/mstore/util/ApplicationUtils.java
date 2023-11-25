package com.mstore.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.mstore.model.User;
import com.mstore.security.CustomeUser;

public class ApplicationUtils {

	/* This Method will Return the Current Logedin User
	 */
	public static User getLogedInUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CustomeUser CustomeUser = (CustomeUser)authentication.getPrincipal();
		return CustomeUser.getUser();
	}
	
	/*
	 * This with return true or false if the given string is null or empty 
	 */
	public static boolean isNotNullOrEmpty(String value) {
		return ((value == null) || value.equals("") || value.isBlank() || value.isEmpty()) ;
	}
	
	/*
	 * 
	 */
	public static Double getDiscountedPrice(Double price , int discountPercent) {
		return price - (price*discountPercent)/100;
	}
	
	public static int getDiscountPercent(Double price , Double discountPrice) {
		Double  difference =  (price - discountPrice);
		return (int)((difference/price)*100);
	}
}
