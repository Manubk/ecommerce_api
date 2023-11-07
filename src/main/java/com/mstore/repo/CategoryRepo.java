package com.mstore.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mstore.model.Category;


@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {

	public Category findByName(String name);
}
