package com.example.food_web.repository;

import com.example.food_web.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFoodRepository extends JpaRepository<Food, Long> {
}
