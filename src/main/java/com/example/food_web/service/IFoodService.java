package com.example.food_web.service;

import com.example.food_web.model.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IFoodService extends IGenerateService<Food, Long>{
    Page<Food> findAllPage(Pageable pageable);

    Page<Food> findProductByNameAbout(String name, Pageable pageable);

    Page<Food> filter(Long min, Long max, String name, Pageable pageable);

    Page<Food> sortByPriceASC(Pageable pageable);
    Page<Food> sortByPriceDSC(Pageable pageable);

}
