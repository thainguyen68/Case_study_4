package com.example.food_web.controller;

import com.example.food_web.model.Bill;
import com.example.food_web.model.Bill_food;
import com.example.food_web.model.Food;
import com.example.food_web.model.User;
import com.example.food_web.service.IBillFoodService;
import com.example.food_web.service.IBillService;
import com.example.food_web.service.IFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/bills")
public class BillController {
    @Autowired
    public IBillService billService;
    @Autowired
    public IFoodService foodService;

    @Autowired
    public IBillFoodService billFoodService;

    @GetMapping
    public ResponseEntity<List<Bill>> findAllBill() {
        List<Bill> bills = billService.findAll();
        if (!bills.isEmpty()) {
            return new ResponseEntity<>(bills, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Bill>> findOneBill(@PathVariable Long id) {
        Optional<Bill> billOptional = billService.findOne(id);
        if (billOptional.isPresent()) {
            return new ResponseEntity<>(billOptional, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


//    @PostMapping
//    public ResponseEntity<?> createBill(@RequestPart Bill bill,
//                                        @RequestPart("food1") Food food) {
//        Optional<Bill> billsByUserId = billService.findBillByUserId(bill.getUser().getId());
//        List<Food> foodList;
//        if (billsByUserId.isPresent()){
//            List<Food> foodListCheck = billsByUserId.get().getFood();
//            foodList = foodListCheck;
//            bill.setId(billsByUserId.get().getId());
//        } else {
//            foodList = new ArrayList<>();
//        }
//        Optional<Food> foodOptional = foodService.findOne(food.getId().longValue());
//        Food food1 = foodOptional.get();
//        foodList.add(food1);
//
//
//        bill.setStatus(false);
//        bill.setFood(foodList);
//        billService.save(bill);
//
//        return new ResponseEntity<>(HttpStatus.ACCEPTED);
//    }


//    @PostMapping
//    public ResponseEntity<?> createBill(
//                                        @RequestPart User user,
//                                        @RequestPart("food1") Food food) {
//
//        Optional<Bill> billsByUserId = billService.findBillByUserId(user.getId());
//        if (billsByUserId.isPresent()) {
//            // viet lại thanh truy ván List, duyệt mảng bill kiểm tra xem food1 kia đã tồn tại chưa
//
//            Optional<Bill_food> bill_food = billFoodService.findByBillIdAndFoodId(billsByUserId.get().getId(), food.getId());
//            if (bill_food.isPresent()) {
//                bill_food.get().setQuantity(bill_food.get().getQuantity() + 1);
//                billFoodService.save(bill_food.get());
//            } else {
//                Bill newBill = new Bill();
//                newBill.setStatus(false);
//                newBill.setUser(user);
//                newBill.setTotal(0D);
//                billService.save(newBill);
//
//                Bill_food b_food = new Bill_food();
//                b_food.setBill(newBill);
//                b_food.setFood(food);
//                b_food.setQuantity(1);
//                billFoodService.save(b_food);
//            }
//        } else {
//            Bill newBill = new Bill();
//            newBill.setStatus(false);
//            newBill.setUser(user);
//            newBill.setTotal(0D);
//            billService.save(newBill);
//
//            Bill_food b_food = new Bill_food();
//            b_food.setBill(newBill);
//            b_food.setFood(food);
//            b_food.setQuantity(1);
//            billFoodService.save(b_food);
//        }
//        return new ResponseEntity<>(HttpStatus.ACCEPTED);
//    }


    @PostMapping
    public ResponseEntity<?> createBill(
            @RequestPart User user,
            @RequestPart("food1") Food food) {

        List<Bill> billsByUserId = billService.findBillByUserId(user.getId());
        if (!billsByUserId.isEmpty()) {
            List<Food> foodList;
            for (Bill bill : billsByUserId) {
                foodList = bill.getFood();
                for (Food f : foodList) {
                    if (f.getId().equals(food.getId())) {
                        Optional<Bill_food> bill_food = billFoodService.findByBillIdAndFoodId(bill.getId(), food.getId());
                        if (bill_food.isPresent()) {
                            bill_food.get().setQuantity(bill_food.get().getQuantity() + 1);
                            billFoodService.save(bill_food.get());
                        }
                    } else {
                        Bill newBill = new Bill();
                        newBill.setStatus(false);
                        newBill.setUser(user);
                        newBill.setTotal(0D);
                        billService.save(newBill);

                        Bill_food b_food = new Bill_food();
                        b_food.setBill(newBill);
                        b_food.setFood(food);
                        b_food.setQuantity(1);
                        billFoodService.save(b_food);
                    }
                }
            }
        } else {
            Bill newBill = new Bill();
            newBill.setStatus(false);
            newBill.setUser(user);
            newBill.setTotal(0D);
            billService.save(newBill);

            Bill_food b_food = new Bill_food();
            b_food.setBill(newBill);
            b_food.setFood(food);
            b_food.setQuantity(1);
            billFoodService.save(b_food);
        }
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }


//    @PutMapping("/{id}")
//    public ResponseEntity<?> updateBill(@PathVariable Long id,
//                                        @RequestBody Bill bill) {
//        Optional<Bill> billOptional = billService.findOne(id);
//        if (billOptional.isPresent()) {
//            bill.setId(id);
//            billService.save(bill);
//            return new ResponseEntity<>(HttpStatus.ACCEPTED);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBill(@PathVariable Long id) {
        Optional<Bill> billOptional = billService.findOne(id);
        if (billOptional.isPresent()) {
            billService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/pay")
    public ResponseEntity<Optional<Bill>> payBill(@RequestPart Bill bill) {
        Optional<Bill> billOptional = billService.findOne(bill.getId());
        if (billOptional.isPresent()) {
            bill.setStatus(true);
            billService.save(bill);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
