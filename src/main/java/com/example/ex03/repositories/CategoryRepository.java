package com.example.ex03.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ex03.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{
	// check existed name
		List<Category> findByName(String name);
		// phan trang
		Page<Category> findByName(String name, Pageable pageable);
}
