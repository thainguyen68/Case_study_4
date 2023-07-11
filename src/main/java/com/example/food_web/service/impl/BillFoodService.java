package com.example.food_web.service.impl;


import com.example.food_web.model.Bill_food;
import com.example.food_web.repository.IBillFoodRepository;
import com.example.food_web.service.IBillFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BillFoodService implements IBillFoodService {

    @Autowired
    public IBillFoodRepository iBillFoodRepository;

    @Override
    public List<Bill_food> findAll() {
        return iBillFoodRepository.findAll();
    }

    @Override
    public Optional<Bill_food> findOne(Long aLong) {
        return Optional.empty();
    }

    @Override
    public void save(Bill_food billFood) {
        iBillFoodRepository.save(billFood);
    }

    @Override
    public void delete(Long aLong) {
        iBillFoodRepository.deleteById(aLong);
    }

    @Override
    public Optional<Bill_food> findByBillIdAndFoodId(Long billId, Long foodId) {
        return iBillFoodRepository.findByBillIdAndFoodId(billId,foodId);
    }
}
