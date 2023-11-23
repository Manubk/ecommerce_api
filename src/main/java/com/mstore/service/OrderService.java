package com.mstore.service;

import java.util.List;

import com.mstore.dto.OrderDto;
import com.mstore.exception.OrderException;
import com.mstore.model.Order;
import com.mstore.response.GeneralResponse;

public interface OrderService {

	public GeneralResponse createOrder(OrderDto orderDto);
	
	public Order findOrderById(Long orderId) throws OrderException;

	public List<OrderDto> getUserOrderDtoOfUser();
	
	public GeneralResponse changeOrderStatus(String status,Long orderId); //DELEVER , SHIPPED , PENDING
	
	public List<Order> getAllOrdersOfUser(Long userId); //ADMIN , SELLER
	 
	public Void deleteOrderById(Long orderId) throws OrderException;  //ADMIN
	
}
