package com.example.food_web.service.impl;

import com.example.food_web.model.Food;
import com.example.food_web.repository.IFoodRepository;
import com.example.food_web.service.IBillService;
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

    @Autowired
    public IBillService iBillService;
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
        Optional<Food> foodOptional = findOne(aLong);
//        List<Bill> billListByFoodId = iBillService.findAllBillByFoodId(aLong);
//        List<Bill> billLists = iBillService.findAll();
        if (foodOptional.isPresent()){
//            billLists.removeAll(billListByFoodId);
//            iBillService.deleteBillFood(aLong);
            iFoodRepository.deleteById(aLong);
        }

    }

    @Override
    public Page<Food> findAllPage(Pageable pageable) {
        return iFoodRepository.findAll(pageable);
    }

    @Override
    public Page<Food> findProductByNameAbout(String name, Pageable pageable) {
        return iFoodRepository.findProductByNameAbout("%" + name + "%", pageable);
    }


    @Override
    public Page<Food> filter(Long min, Long max, String name, Pageable pageable) {
        return iFoodRepository.filter(min, max,"%" + name + "%", pageable);
    }

    @Override
    public Page<Food> sortByPriceASC(Pageable pageable) {
        return iFoodRepository.findAllByOrderByPriceAsc(pageable);
    }

    @Override
    public Page<Food> sortByPriceDSC(Pageable pageable) {
        return iFoodRepository.findAllByOrderByPriceDesc(pageable);
    }

}
