package com.example.food_web.controller;

import com.example.food_web.model.Bill;
import com.example.food_web.model.Food;
import com.example.food_web.service.IBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/bills")
public class BillController {
    @Autowired
    public IBillService billService;

    @GetMapping
    public ResponseEntity<List<Bill>> findAllBill() {
        List<Bill> bills = billService.findAll();
        if (!bills.isEmpty()) {
            return new ResponseEntity<>(bills,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Bill>> findOneBill(@PathVariable Long id) {
        Optional<Bill> billOptional = billService.findOne(id);
        if (billOptional.isPresent()) {
            return new ResponseEntity<>(billOptional,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping
    public ResponseEntity<?> createBill(@RequestPart Bill bill,
                                        @RequestPart("food1") Food food) {
        Optional<Bill> billsByUserId = billService.findBillByUserId(bill.getUser().getId());
        List<Food> foodList;
        if (billsByUserId.isPresent()){
            List<Food> foodListCheck = billsByUserId.get().getFood();
            foodList = foodListCheck;
            bill.setId(billsByUserId.get().getId());
        } else {
            foodList = new ArrayList<>();
        }
        bill.setStatus(false);
        foodList.add(food);
        bill.setFood(foodList);
        billService.save(bill);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }




    @PutMapping("/{id}")
    public ResponseEntity<?> updateBill(@PathVariable Long id,
                                        @RequestBody Bill bill) {
        Optional<Bill> billOptional = billService.findOne(id);
        if (billOptional.isPresent()) {
            bill.setId(id);
            billService.save(bill);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBill(@PathVariable Long id){
        Optional<Bill> billOptional = billService.findOne(id);
        if (billOptional.isPresent()){
            billService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
