package com.mstore.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mstore.constants.ApplicationConstants;
import com.mstore.dto.OrderDto;
import com.mstore.exception.OrderException;
import com.mstore.exception.OrderNotFoundException;
import com.mstore.exception.UserNotFoundException;
import com.mstore.model.Address;
import com.mstore.model.Cart;
import com.mstore.model.CartItem;
import com.mstore.model.Order;
import com.mstore.model.OrderItem;
import com.mstore.model.PayMentInformation;
import com.mstore.model.Product;
import com.mstore.model.User;
import com.mstore.repo.AddressRepo;
import com.mstore.repo.CartItemRepo;
import com.mstore.repo.CartRepo;
import com.mstore.repo.OrderItemRepo;
import com.mstore.repo.OrderRepo;
import com.mstore.repo.PaymentInformationRepo;
import com.mstore.repo.UserRepo;
import com.mstore.response.GeneralResponse;
import com.mstore.service.CartService;
import com.mstore.service.OrderService;
import com.mstore.util.ApplicationUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private OrderRepo orderRepo;
	
	@Autowired
	private OrderItemRepo orderdItemRepo;
	
	@Autowired
	private AddressRepo addressRepo;
	
	@Autowired
	private PaymentInformationRepo paymentInformationRepo;

	@Autowired
	private CartRepo cartRepo;

	@Autowired
	private CartService cartService;

	@Autowired
	private CartItemRepo cartItemRepo;
	
	

	@Override
	public GeneralResponse createOrder(OrderDto orderDto) {
		User logedInUser = ApplicationUtils.getLogedInUser();
		log.info("Creating new Order for="+logedInUser.getEmail());
		
		//First Get the Items present in cart 
		Cart cart = cartRepo.findByUserId(logedInUser.getId());
		List<CartItem> cartItems = cart.getCartItems();
		List<CartItem> toRemoveCateItem = new ArrayList<CartItem>();

		//Creating the orderitems from cartitems
		List<OrderItem> orderItems = new ArrayList<OrderItem>();

		//Getting Required  Objects
		Optional<Address> shippingAddress = addressRepo.findById(orderDto.getAddressId());
		Optional<PayMentInformation> paymentInformation = paymentInformationRepo.findById(orderDto.getPaymentId());
			
		//copying cartitems to orderitems
			for(CartItem item : cartItems){
			if(!item.isSaveForLater()) {
			OrderItem orderItem = new OrderItem();
			BeanUtils.copyProperties(item, orderItem);
			orderItems.add(orderItem);
			toRemoveCateItem.add(item);
			}
		}
			//Creating the new order
			Order  order = new Order();
			order.setStatus(ApplicationConstants.CREATED);
			order.setPaymentInformation(paymentInformation.get());
			order.setShippingAddress(shippingAddress.get());
			order.getOrderItems().addAll(orderItems);
			order = updateOrderDetails(order);
			
			//Persisting the order
			order.setUser(logedInUser);
			Order savedOrder = orderRepo.save(order);
		
		if(savedOrder != null) {
			
			//Setting Order to all OrderItems
			orderItems.stream().forEach(item -> {
				item.setOrder(savedOrder);
			});

			//Persisting the orderItems
			orderItems = orderdItemRepo.saveAll(orderItems);
		
			
			if(orderItems != null){

				//Update the product quantity
				toRemoveCateItem.forEach(cartItem -> {
					Product product = cartItem.getProduct();

					product.getSizes().forEach(size -> {
						if(size.getName().equalsIgnoreCase(cartItem.getSize())){
							size.setQuantity(size.getQuantity() - cartItem.getQuantity());
						}
					});
					
				});

				//Removing the cart item from database
				cartItemRepo.deleteAll(toRemoveCateItem);

				//Updating the cart 
				cart.getCartItems().removeAll(toRemoveCateItem);
				cart = cartService.updateCartAfterChange(cart);
				cartRepo.save(cart);

				return new GeneralResponse.GeneralResposeBuilder().setIsSuccess(true).setMessage("Order Created SuccessFully ;)").build();
			}
			return new GeneralResponse.GeneralResposeBuilder().setIsSuccess(false).setMessage("Unable to create order").build();
		}else {
			log.warn("OrderIntem Save Exception ");
			return new GeneralResponse.GeneralResposeBuilder().setIsSuccess(true).setMessage("Error in Creating Order ;)").build();
		}
		
	}

	@Override
	public Order findOrderById(Long orderId) throws OrderNotFoundException {
		log.info("Getting Order By id="+orderId);
		
		Optional<Order> orderOpt = orderRepo.findById(orderId);
		
		if(orderOpt.isPresent()) {
			return orderOpt.get();
		}else {
			throw new OrderNotFoundException();
		}

	}

	@Override
	public List<OrderDto> getUserOrderDtoOfUser() {
		User logedInUser = ApplicationUtils.getLogedInUser();
		log.info("Getting the Order History of User");
		
		List<Order> ordersByUser = orderRepo.findByUser(logedInUser);
		List<OrderDto> orderDtosOfUser = new ArrayList<OrderDto>();

		ordersByUser.forEach(order -> {
			OrderDto orderDto = new OrderDto();
			BeanUtils.copyProperties(order, orderDto);
			orderDto.setAddressId(order.getShippingAddress().getId());
			orderDto.setOrderItems(order.getOrderItems());
			orderDto.setPaymentId(order.getPaymentInformation().getId());
			orderDto.setUserId(logedInUser.getId());
			orderDtosOfUser.add(orderDto);
		});
		
		return orderDtosOfUser;
	}

	@Override
	public GeneralResponse changeOrderStatus(String status, Long orderId) {
		log.info("Changing Order Status="+status);
		
		Optional<Order> order = orderRepo.findById(orderId);
		
		if(order.isPresent()) {
			if(status.equalsIgnoreCase(ApplicationConstants.CREATED) || status.equalsIgnoreCase(ApplicationConstants.DELIVERED) ||
					status.equalsIgnoreCase(ApplicationConstants.OUT_FOR_DELIVERY) || status.equalsIgnoreCase(ApplicationConstants.SHIPPED)){
				order.get().setStatus(status);
				
				Order savedOrder = orderRepo.save(order.get());
				
				
				if(savedOrder != null)
					return new GeneralResponse.GeneralResposeBuilder().setIsSuccess(true).setMessage("Order Status Changed to "+status).build();
			}
		}else {
			throw new OrderNotFoundException();
		}
			
		
		return new GeneralResponse.GeneralResposeBuilder().setIsSuccess(false).setMessage("Unable to change the order status ;{").build();
	}

	@Override
	public List<Order> getAllOrdersOfUser(Long userId) {
		log.info("Getting all the orders of userId="+userId);
		
		Optional<User> user = userRepo.findById(userId);
		
		if(user.isPresent()) {
			List<Order> orders = orderRepo.findByUser(user.get());
			return orders;
		}else {
			throw new UserNotFoundException();
		}

	}

	@Override
	public Void deleteOrderById(Long orderId) throws OrderException {
		// TODO Auto-generated method stub
		return null;
	}


	private Order updateOrderDetails(Order order) {
		log.info("Updating the orderDetails");
		List<OrderItem> orderItems = order.getOrderItems();
		
		double totalPrice = 0;
		double totalDiscountedPrice = 0;
		
		for(OrderItem item : order.getOrderItems()) {
			totalPrice+=item.getPrice();
			totalDiscountedPrice+= item.getDiscountedPrice();
		}
	
		order.setTotalPrice(totalPrice);
		order.setTotalDiscountedPrice(totalDiscountedPrice);
		
		return order;
	}

	
	
}
