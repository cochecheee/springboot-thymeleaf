package com.example.ex03.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.ex03.entities.Category;
import com.example.ex03.repositories.CategoryRepository;

import io.micrometer.common.util.StringUtils;

@Service
public class CategoryServiceImpl implements com.example.ex03.services.ICategoryService {
	@Autowired
	CategoryRepository categoryRepository;

	public CategoryServiceImpl(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	@Override
	public <S extends Category> S save(S entity) {
		if (entity.getId() == 0) {
			return categoryRepository.save(entity);
		} else {
			Optional<Category> opt = findById(entity.getId());
			if (opt.isPresent()) {
				if (StringUtils.isEmpty(entity.getName())) {
					entity.setName(opt.get().getName());
				} else {
					entity.setName(entity.getName());
				}
			}
			return categoryRepository.save(entity);
		}

	}

	@Override
	public List<Category> findAll() {
		return categoryRepository.findAll();
	}

	@Override
	public Optional<Category> findById(Integer id) {
		return categoryRepository.findById(id);
	}

	@Override
	public long count() {
		return categoryRepository.count();
	}

	@Override
	public void deleteById(Integer id) {
		categoryRepository.deleteById(id);
	}

	@Override
	public List<Category> findByName(String name) {
		return categoryRepository.findByName(name);
	}

	@Override
	public Page<Category> findByName(String name, Pageable pageable) {
		return categoryRepository.findByName(name, pageable);
	}
}