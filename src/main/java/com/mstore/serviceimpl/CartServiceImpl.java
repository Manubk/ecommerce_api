package com.mstore.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mstore.dto.CartDto;
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
public class CartServiceImpl implements CartService {

	@Autowired
	private CartRepo cartRepo;

	@Autowired
	private CartItemRepo cartItemRepo;

	@Autowired
	private ProductRepo productRepo;

	Logger log = LoggerFactory.getLogger(CartServiceImpl.class);

	@Override
	public Cart createCart(User user) {
		log.info("Creating a cart for User id=" + user.getId());

		try {
			Cart cart = new Cart();
			cart.setUser(user);
			Cart savedCart = cartRepo.save(cart);
			return savedCart;
		} catch (Exception e) {
			log.error("Exception In creating Cart for user id=" + user.getId());
			log.error(e.getMessage());
		}
		return null;

	}

	@Override
	public GeneralResponse addCartItem(Long userId, CartItemDto cartItemDto) throws ProductException {

		User logedInUser = ApplicationUtils.getLogedInUser();
		log.info("Adding CartItem to Cart userId=" + userId);

		Cart cart = cartRepo.findByUserId(logedInUser.getId());

		if (cart != null) {

			// Creating New Cart Item
			CartItem cartItem = new CartItem();

			// Finding the Product
			Optional<Product> productOpt = productRepo.findById(cartItemDto.getProductId());
			Product product = productOpt.get();

			// Setting all the values for CartItem
			cartItem.setCart(cart);
			cartItem.setProduct(product);
			cartItem.setPrice(product.getPrice() * cartItemDto.getQuantity());
			cartItem.setQuantity(cartItemDto.getQuantity());
			cartItem.setSize(cartItemDto.getSize());
			cartItem.setDiscountPercentage(
					ApplicationUtils.getDiscountPercent(product.getPrice(), product.getDiscountedPrice()));
			cartItem.setDiscountedPrice(product.getDiscountedPrice() * cartItem.getQuantity());

			// Now we should change cart values
			cart.getCartItems().add(cartItem);
			cart.setTotalItem(cart.getTotalItem() + 1);
			cart.setTotalPrice(cart.getTotalPrice() + cartItem.getPrice());
			cart.setTotalDiscountedPrice(cart.getTotalDiscountedPrice() + cartItem.getDiscountedPrice());
			cart.setDiscount(ApplicationUtils.getDiscountPercent(cart.getTotalPrice(), cart.getTotalDiscountedPrice()));

			// Saving the Cart Values
			Cart savedCart = cartRepo.save(cart);

			return new GeneralResponse.GeneralResposeBuilder().setIsSuccess(true)
					.setMessage("Item Addded Successfull ;)").build();
		} else {
			throw new CartNotPresentException();
		}
	}

	@Override
	public Cart findCartByUser(Long userId) {
		log.info("Finding cart from user id=" + userId);
		try {
			Cart cart = cartRepo.findByUserId(userId);
			if (cart == null) {
				throw new CartNotFoundException("Cart Not Found ;(");
			} else
				return cart;

		} catch (Exception e) {
			log.error("Cart Not Present for User id=" + userId);

		}
		return null;
	}

	@Override
	public GeneralResponse removeCartItem(Long itemId) {
		try {

			User logedInUser = ApplicationUtils.getLogedInUser();
			log.info("Removing the Item from cart itemId=" + itemId);

			Cart cart = cartRepo.findByUserId(logedInUser.getId());

			// Getting the item that should be removed
			CartItem itemToRemove = null;
			for (CartItem cartItem : cart.getCartItems()) {
				if (cartItem.getId() == itemId) {
					itemToRemove = cartItem;
					break;
				}
			}

			// updating the cart
			cart.getCartItems().remove(itemToRemove);
			cart.setTotalItem(cart.getTotalItem() - 1);
			cart.setTotalPrice(cart.getTotalPrice() - itemToRemove.getPrice());
			cart.setTotalDiscountedPrice(cart.getTotalDiscountedPrice() - itemToRemove.getDiscountedPrice());
			cart.setDiscount(ApplicationUtils.getDiscountPercent(cart.getTotalPrice(), cart.getTotalDiscountedPrice()));

			Cart save = cartRepo.save(cart);
			cartItemRepo.deleteItemFromCart(itemId);

			return new GeneralResponse.GeneralResposeBuilder().setIsSuccess(true)
					.setMessage("Item Removed SuccessFull ;)").build();

		} catch (CartItemNotFoundException cartItemException) {
			if (cartItemException != null)
				throw new CartItemNotFoundException("Item Not Found in cart");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;

	}

	@Override
	public Cart findUserCart() throws CartNotFoundException {
		User logedInUser = ApplicationUtils.getLogedInUser();
		log.info("Finding Cart Of User=" + logedInUser.getEmail());

		return cartRepo.findByUserId(logedInUser.getId());

	}

	@Override
	public GeneralResponse saveForLater(Long itemId) {
		log.info("saveForLater item=" + itemId);
		User logedInUser = ApplicationUtils.getLogedInUser();
		Cart cart = cartRepo.findByUserId(logedInUser.getId());
		List<CartItem> cartItems = cart.getCartItems();

		cartItems.forEach(item -> {
			if (item.getId() == itemId) {
				item.setSaveForLater(true);

				// Updating the cart values
				updateCartAfterChange(cart);

				// Saving the data to database
				Cart save = cartRepo.save(cart);

			}
		});
		return new GeneralResponse.GeneralResposeBuilder().setIsSuccess(true)
				.setMessage("Saved For Later SuccessFull").build();
	}

	

	@Override
	public GeneralResponse increaseQuantity(Long itemId, Integer quantity) {
		User logedInUser = ApplicationUtils.getLogedInUser();
		log.info("Increasing the quantity of the item=" + itemId);

		Cart cart = cartRepo.findByUserId(logedInUser.getId());
		cart.getCartItems().forEach(item -> {
			if (item.getId() == itemId) {
				item.setQuantity(quantity);
			}
		});
		// Cart should be updated after change
		Cart updatedCart = updateCartAfterChange(cart);

		cartRepo.save(updatedCart);
		return new GeneralResponse.GeneralResposeBuilder().setIsSuccess(true)
				.setMessage("Quantity Increased SuccessFull ;)").build();
	}

	@Override
	public CartDto findUserDtoCart() throws CartItemNotFoundException {
		User logedInUser = ApplicationUtils.getLogedInUser();
		log.info("Getting the cart details");

		Cart cart = findUserCart();
		CartDto cartDto = new CartDto();
		BeanUtils.copyProperties(cart, cartDto);
		cartDto.setUser_id(logedInUser.getId());

		return cartDto;
	}


	//Inner application methods
	
	public CartItem updateCartItem(CartItem cartItem) {
		log.info("updateCartItem");
		Product product = cartItem.getProduct();
		cartItem.setPrice(product.getPrice() * cartItem.getQuantity());
		cartItem.setDiscountedPrice(product.getDiscountedPrice() * cartItem.getQuantity());
		return cartItem;
	}

	public Cart updateCartAfterChange(Cart cart) {
		log.info("updating the cart after change");

		double totalPrice = 0;
		int totalItem = 0;
		double totalDiscountedPrice = 0;
		int discount = 0;

		for (CartItem item : cart.getCartItems()) {
			// Updating the each cartItem
			item = updateCartItem(item);
			
			//Adding the amount of the item which is not save for later
			if (!item.isSaveForLater()) {
				totalPrice += item.getPrice();
				totalItem++;
				totalDiscountedPrice += item.getDiscountedPrice();
			}

		}

		//Setting the values to cart
		cart.setTotalItem(totalItem);
		cart.setTotalPrice(totalPrice);
		cart.setTotalDiscountedPrice(totalDiscountedPrice);
		cart.setDiscount(ApplicationUtils.getDiscountPercent(totalPrice, totalDiscountedPrice));

		return cart;
	}
}
