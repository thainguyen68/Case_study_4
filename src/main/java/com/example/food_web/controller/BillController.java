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
//    public ResponseEntity<?> createBill(
//            @RequestPart User user,
//            @RequestPart("food1") Food food) {
//
//        List<Bill> billsByUserId = billService.findBillByUserId(user.getId());
//        if (!billsByUserId.isEmpty()) {
//            List<Food> foodList;
//            for (Bill bill : billsByUserId) {
//                foodList = bill.getFood();
//                for (Food f : foodList) {
//                    if (f.getId().equals(food.getId())) {
//                        Optional<Bill_food> bill_food = billFoodService.findByBillIdAndFoodId(bill.getId(), food.getId());
//                        if (bill_food.isPresent()) {
//                            bill_food.get().setQuantity(bill_food.get().getQuantity() + 1);
//                            billFoodService.save(bill_food.get());
//                        }
//                    } else {
//                        Bill newBill = new Bill();
//                        newBill.setStatus(false);
//                        newBill.setUser(user);
//                        newBill.setTotal(0D);
//                        billService.save(newBill);
//
//                        Bill_food b_food = new Bill_food();
//                        b_food.setBill(newBill);
//                        b_food.setFood(food);
//                        b_food.setQuantity(1);
//                        billFoodService.save(b_food);
//                    }
//                }
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
        Optional<Food> f = foodService.findOne(food.getId());
        Double total = 0D;
        List<Bill> billsByUserId = billService.findBillByUserId(user.getId());
        if (!billsByUserId.isEmpty()) {
            for (Bill bill : billsByUserId) {
                Optional<Bill_food> bill_food = billFoodService.findByBillIdAndFoodId(bill.getId(), food.getId());
                Optional<Food> foodOptional  = foodService.findOne(food.getId());
                if (bill_food.isPresent()) {
                    bill_food.get().setQuantity(bill_food.get().getQuantity() + 1);
                    f.get().setQuantity(f.get().getQuantity()-1);

                    total = bill_food.get().getQuantity() * foodOptional.get().getPrice();
                    total += bill.getTotal() == null ? 0 : bill.getTotal();
                    bill.setTotal(total);
                    billService.save(bill);
                    foodService.save(f.get());
                    billFoodService.save(bill_food.get());
                }
                else{
                    Bill_food b_food = new Bill_food();
                    b_food.setBill(bill);
                    b_food.setFood(food);
                    b_food.setQuantity(1);
                    f.get().setQuantity(f.get().getQuantity()-1);


                    total = b_food.getQuantity() * foodOptional.get().getPrice();
                    bill.setTotal(bill.getTotal() + total);
                    billService.save(bill);
                    foodService.save(f.get());
                    billFoodService.save(b_food);
                }
            }
        }
        else{
            Bill newBill = new Bill();
            newBill.setStatus(false);
            newBill.setUser(user);

            billService.save(newBill);

            Bill_food b_food = new Bill_food();
            b_food.setBill(newBill);
            b_food.setFood(food);
            b_food.setQuantity(1);
            newBill.setTotal(f.get().getPrice() * b_food.getQuantity());

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
//            billFoodService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/pay/{id}")
    public ResponseEntity<Optional<Bill>> payBill(@PathVariable Long id) {
        Optional<Bill> billOptional = billService.findOne(id);
        if (billOptional.isPresent()) {
            billOptional.get().setStatus(true);
            billService.save(billOptional.get());
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
