package com.example.food_web.service;

import com.example.food_web.model.Bill_food;

import java.util.Optional;

public interface IBillFoodService extends IGenerateService<Bill_food, Long>{
    Optional<Bill_food> findByBillIdAndFoodId(Long billId , Long foodId);
}
