package com.example.food_web.service;

import com.example.food_web.model.Bill;

import java.util.List;
import java.util.Optional;

public interface IBillService extends IGenerateService<Bill,Long>{
    List<Bill> findAllBillByFoodId(Long id);
    void deleteBillFood(Long id);

//    Optional<Bill> findBillByUserId(Long userId);

    List<Bill> findBillByUserId(Long userId);

    Integer totalQuantity(Long id);
}
