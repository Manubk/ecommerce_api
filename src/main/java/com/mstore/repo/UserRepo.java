package com.mstore.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mstore.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

	public User findByEmail(String email);
}
