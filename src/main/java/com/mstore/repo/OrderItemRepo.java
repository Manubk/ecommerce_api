package com.mstore.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mstore.model.OrderItem;

public interface OrderItemRepo extends JpaRepository<OrderItem, Long> {

}
