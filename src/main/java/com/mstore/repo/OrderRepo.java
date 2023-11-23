package com.mstore.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mstore.model.Order;
import com.mstore.model.User;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long>{

	public List<Order> findByUser(User user);
	
	
}
