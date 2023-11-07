package com.mstore.serviceimpl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mstore.dto.CartItemDto;
import com.mstore.exception.CartItemNotFoundException;
import com.mstore.exception.CartNotFoundException;
import com.mstore.exception.CartNotPresentException;
import com.mstore.exception.ProductException;
import com.mstore.model.Cart;
import com.mstore.model.CartItem;
import com.mstore.model.Product;
import com.mstore.model.User;
import com.mstore.repo.CartItemRepo;
import com.mstore.repo.CartRepo;
import com.mstore.repo.ProductRepo;
import com.mstore.response.GeneralResponse;
import com.mstore.service.CartService;
import com.mstore.util.ApplicationUtils;

@Service
public class CartServiceImpl implements CartService{
	
	@Autowired
	private CartRepo cartRepo;
	
	@Autowired
	private CartItemRepo cartItemRepo;
	
	@Autowired
	private ProductRepo productRepo;
	
	Logger log = LoggerFactory.getLogger(CartServiceImpl.class);

	@Override
	public Cart createCart(User user) {
		log.info("Creating a cart for User id="+user.getId());
		
		try {
			Cart cart = new Cart();
			cart.setUser(user);
			Cart savedCart = cartRepo.save(cart);
			return savedCart;
		} catch (Exception e) {
			log.error("Exception In creating Cart for user id="+user.getId());
			log.error(e.getMessage());
		}
		return null;
		
	}

	@Override
	public GeneralResponse addCartItem(Long userId, CartItemDto cartItemDto) throws ProductException {
		User logedInUser = ApplicationUtils.getLogedInUser();
		log.info("Adding CartItem to Cart userId="+userId);
		
		Cart cart = logedInUser.getCart();
		
		if(cart != null) {
			
			//Creating New Cart Item 
			CartItem cartItem = new CartItem();
			
			//Finding the Product 
			Optional<Product> productOpt = productRepo.findById(cartItemDto.getProductId());
			Product product = productOpt.get();
			
	        
			//Setting all the values for CartItem
			cartItem.setCart(cart);
		    cartItem.setProduct(product);
			cartItem.setPrice(product.getPrice()*cartItemDto.getQuantity());
			cartItem.setQuantity(cartItemDto.getQuantity());
			cartItem.setSize(cartItemDto.getSize());
			cartItem.setDiscountPercentage(ApplicationUtils.getDiscountPercent(product.getPrice(), product.getDiscountedPrice()));
			cartItem.setDiscountedPrice(product.getDiscountedPrice()*cartItem.getQuantity());
			
			//Now we should change cart values 
			cart.getCartItems().add(cartItem);
			cart.setTotalItem(cart.getTotalItem()+1);
			cart.setTotalPrice(cart.getTotalPrice()+ cartItem.getPrice());
			cart.setTotalDiscountedPrice(cart.getTotalDiscountedPrice()+cartItem.getDiscountedPrice());
			cart .setDiscount(ApplicationUtils.getDiscountPercent(cart.getTotalPrice(), cart.getTotalDiscountedPrice()));
			
			//Saving the Cart Values 
			Cart savedCart = cartRepo.save(cart);
			
			return new GeneralResponse.GeneralResposeBuilder().setIsSuccess(true).setMessage("Item Addded Successfull ;)").build();
		}else {
			throw new CartNotPresentException();
		}
	}

	@Override
	public Cart findCartByUser(Long userId) {
		log.info("Finding cart from user id="+userId);
		try {
		Cart cart = cartRepo.findByUserId(userId);
		}catch (Exception e) {
			log.error("Cart Not Present for User id="+userId);
			
		}
		return null;
	}

	@Override
	public GeneralResponse removeCartItem(Long itemId) {
		User logedInUser = ApplicationUtils.getLogedInUser();
		log.info("Removing the Item from cart itemId="+itemId);
		
		Cart cart = logedInUser.getCart();
		
		//Getting the item that should be removed
		CartItem itemToRemove = null;
		for(CartItem cartItem :cart.getCartItems()) {
			if(cartItem.getId() == itemId) {
				itemToRemove = cartItem;
				break;
			}
		}
		
		//Removing the item
		if(itemToRemove != null) {
			cart.getCartItems().remove(itemToRemove);	
			cartItemRepo.deleteById(itemToRemove.getId());
		}
		
		//updating the cart 
		cart.setTotalItem(cart.getTotalItem()-1);
		cart.setTotalPrice(cart.getTotalPrice()-itemToRemove.getPrice());
		cart.setTotalDiscountedPrice(cart.getTotalDiscountedPrice()-itemToRemove.getDiscountedPrice());
		cart.setDiscount(ApplicationUtils.getDiscountPercent(cart.getTotalPrice(),cart.getTotalDiscountedPrice()));
		
		cartRepo.save(cart);
		
		return new GeneralResponse.GeneralResposeBuilder().setIsSuccess(true).setMessage("Item Removed SuccessFull ;)").build();
	}

	@Override
	public Cart findUserCart() throws CartNotFoundException {
		User logedInUser = ApplicationUtils.getLogedInUser();
		log.info("Finding Cart Of User="+logedInUser.getEmail());
		
		return logedInUser.getCart(); 	
	
	}

	public Cart updateCardAfterChange(Cart cart) {
		log.info("updating the cart after change");
		
		cart.getCartItems().forEach(item ->{
			
			if(!item.isSaveForLater()) {
				//TODO
			}
		});
		return null;
	}
}
