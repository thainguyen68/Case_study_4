package com.example.food_web.service.impl;

import com.example.food_web.model.Food;
import com.example.food_web.repository.IFoodRepository;
import com.example.food_web.service.IFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class FoodService implements IFoodService {
    @Autowired
    public IFoodRepository iFoodRepository;
    @Override
    public List<Food> findAll() {
        return iFoodRepository.findAll() ;
    }

    @Override
    public Optional<Food> findOne(Long aLong) {
        return iFoodRepository.findById(aLong);
    }

    @Override
    public void save(Food food) {
        iFoodRepository.save(food);
    }

    @Override
    public void delete(Long aLong) {
        iFoodRepository.deleteById(aLong);
    }

    @Override
    public Page<Food> findAllPage(Pageable pageable) {
        return iFoodRepository.findAll(pageable);
    }
}
