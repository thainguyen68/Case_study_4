package com.example.food_web.service.impl;

import com.example.food_web.model.Bill;
import com.example.food_web.repository.IBillRepository;
import com.example.food_web.service.IBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BillService implements IBillService {
    @Autowired
    public IBillRepository iBillRepository;
    @Override
    public List<Bill> findAll() {
        return iBillRepository.findAll() ;
    }

    @Override
    public Optional<Bill> findOne(Long aLong) {
        return iBillRepository.findById(aLong);
    }

    @Override
    public void save(Bill bill) {
        iBillRepository.save(bill);
    }

    @Override
    public void delete(Long aLong) {
       iBillRepository.deleteById(aLong);
    }


    @Override
    public List<Bill> findAllBillByFoodId(Long id) {
        return iBillRepository.findAllBillByFood_id(id);
    }

    @Override
    public void deleteBillFood(Long id) {
        iBillRepository.deleteBill_food(id);
    }

    @Override
    public List<Bill> findBillByUserId(Long userId) {
        return iBillRepository.findBillByUserId(userId);
    }

    @Override
    public Integer totalQuantity(Long id) {
       return iBillRepository.totalQuantity(id);
    }


}
