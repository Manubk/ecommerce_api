package com.mstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mstore.dto.OrderDto;
import com.mstore.exception.OrderException;
import com.mstore.exception.OrderNotFoundException;
import com.mstore.model.Order;
import com.mstore.model.OrderItem;
import com.mstore.model.Product;
import com.mstore.repo.OrderItemRepo;
import com.mstore.response.GeneralResponse;
import com.mstore.service.OrderService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/")
@Slf4j
public class OrderController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderItemRepo itemRepo;
	
	@PostMapping("/order/create")
	public ResponseEntity<GeneralResponse> createOrder(@RequestBody OrderDto orderDto){
		log.info("creating New Order");
		
		GeneralResponse generalResponse = orderService.createOrder(orderDto);
		
		return new ResponseEntity<GeneralResponse>(generalResponse,HttpStatus.CREATED);	
	}
	
	//ADMIN , SELLER
	@PutMapping("/order/status")
	public ResponseEntity<GeneralResponse> changeOrderStatus(@Param(value = "status")String status,@Param(value = "orderId")Long orderId){
		log.info("changing order Status");
		
		GeneralResponse generalResponse = orderService.changeOrderStatus(status, orderId);
		
		return new ResponseEntity<GeneralResponse>(generalResponse,HttpStatus.OK);
	}
	
	@GetMapping("/order/history")
	public ResponseEntity<List<OrderDto>> getOrderDtoOfUser(){
		log.info("Getting order history of User");
		
		List<OrderDto> userOrderDtos = orderService.getUserOrderDtoOfUser();
		
		return new ResponseEntity<List<OrderDto>>(userOrderDtos,HttpStatus.OK);
	}
	
	@GetMapping("/order/{orderId}")
	public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) throws OrderException{
		log.info("Getting Order By Id="+orderId);
		
		Order order = orderService.findOrderById(orderId);
		
		return new ResponseEntity<Order>(order,HttpStatus.OK);
	}
	
	@GetMapping("/order/user/{userId}")
	public ResponseEntity<List<Order>> getOrderByUserId(@PathVariable Long userId) throws OrderNotFoundException, OrderException{
		log.info("Getting The order by userId="+userId);
		
		 List<Order> orders = orderService.getAllOrdersOfUser(userId);
		
		return new ResponseEntity<List<Order>>(orders,HttpStatus.OK);
	}

	@PostMapping("/save")
	public ResponseEntity<String> save() throws OrderNotFoundException, OrderException{
		log.info("Getting The order by userId=");
		OrderItem item = new OrderItem();
		item.setOrder(new Order());
		item.setProduct(new Product());
		itemRepo.save(item);
		
		return new ResponseEntity<String>("Saved",HttpStatus.OK);
	}
}
