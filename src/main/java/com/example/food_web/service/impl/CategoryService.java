package com.example.food_web.service.impl;

import com.example.food_web.model.Category;
import com.example.food_web.repository.ICategoryRepository;
import com.example.food_web.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CategoryService implements ICategoryService {
    @Autowired
    public ICategoryRepository iCategoryRepository;
    @Override
    public List<Category> findAll() {
        return iCategoryRepository.findAll();
    }

    @Override
    public Optional<Category> findOne(Long aLong) {
        return iCategoryRepository.findById(aLong);
    }

    @Override
    public void save(Category category) {
        iCategoryRepository.save(category);
    }

    @Override
    public void delete(Long aLong) {
        iCategoryRepository.deleteById(aLong);
    }
}
