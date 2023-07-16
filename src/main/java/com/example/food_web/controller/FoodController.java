package com.example.food_web.controller;

import com.example.food_web.model.Food;
import com.example.food_web.service.IFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/foods")
public class FoodController {
    @Autowired
    private IFoodService foodService;

    @Value("${upload-path}")
    private String upload;

    @GetMapping
    public ResponseEntity<Page<Food>> findAllFood(@PageableDefault(size = 10)
                                               Pageable pageable) {
        return new ResponseEntity<>(foodService.findAllPage(pageable), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createFood(@RequestPart Food food,
                                    @RequestPart(required = false) MultipartFile image) {
        String imagePath;
        try {
            if (image != null && !image.isEmpty()) {
                imagePath = image.getOriginalFilename();
                FileCopyUtils.copy(image.getBytes(), new File(upload + imagePath));
                food.setImagePath("/image/" + imagePath);
            } else {
                food.setImagePath("/image/default.jpg");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        foodService.save(food);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateFood(@PathVariable Long id,
                                    @RequestPart Food food,
                                    @RequestPart(required = false) MultipartFile image) {
        Optional<Food> foodOptional = foodService.findOne(id);
        if (foodOptional.isPresent()) {
            String imagePath;
            try {
                if (image != null && !image.isEmpty()) {
                    imagePath = image.getOriginalFilename();
                    FileCopyUtils.copy(image.getBytes(), new File(upload + imagePath));
                    food.setImagePath("/image/" + imagePath);
                } else {
                    food.setImagePath(foodOptional.get().getImagePath());
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            food.setId(id);
            foodService.save(food);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Optional<Food>> deleteFood(@PathVariable Long id) {
        Optional<Food> foodOptional = foodService.findOne(id);
        if (foodOptional.isPresent()) {
            foodService.delete(id);

            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Food>> findOneFood(@PathVariable Long id) {
        return new ResponseEntity<>(foodService.findOne(id), HttpStatus.OK);
    }


    @GetMapping("/search")
    public ResponseEntity<Page<Food>> findProductByNameAbout(@RequestParam("search") String name,
                                                            @PageableDefault(size = 10) Pageable pageable){
        Page<Food> foodIterable = foodService.findProductByNameAbout(name, pageable);
        if (foodIterable.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(foodIterable, HttpStatus.OK);
        }
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<Food>> filter(@RequestParam(required = false, defaultValue = "0") Long min,
                                              @RequestParam(required = false, defaultValue = "999999999") Long max,
                                              @RequestParam(required = false, defaultValue = "") String name,
                                              @PageableDefault(size = 10) Pageable pageable) {
        Page<Food> foodIterable = foodService.filter(min, max, name, pageable);
        if (foodIterable.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(foodIterable, HttpStatus.OK);
        }
    }


    @GetMapping("/sort_price_asc")
    public ResponseEntity<Page<Food>> listFoodByPriceAsc ( @PageableDefault(size = 10) Pageable pageable){
        Page<Food> foodList = foodService.sortByPriceASC(pageable);
        if (foodList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(foodList,HttpStatus.OK);
        }
    }

    @GetMapping("/sort_price_dsc")
    public ResponseEntity<Page<Food>> listFoodByPriceDsc( @PageableDefault(size = 10) Pageable pageable){
        Page<Food> foods =  foodService.sortByPriceDSC(pageable);
        if (foods.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(foods, HttpStatus.OK);
        }
    }

}
