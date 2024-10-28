package com.example.ex03.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.ex03.entities.Category;

public interface ICategoryService {

	Page<Category> findByName(String name, Pageable pageable);

	List<Category> findByName(String name);

	void deleteById(Integer id);

	long count();

	Optional<Category> findById(Integer id);

	List<Category> findAll();

	<S extends Category> S save(S entity);

}
