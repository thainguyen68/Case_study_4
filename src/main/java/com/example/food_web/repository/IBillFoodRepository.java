package com.example.food_web.repository;

import com.example.food_web.model.Bill_food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IBillFoodRepository  extends JpaRepository<Bill_food,Long> {
    @Query(value = "select * from bill_food where bill_id = :bill_id and food_id = :food_id", nativeQuery = true)
    Optional<Bill_food> findByBillIdAndFoodId(@Param("bill_id") Long billId , @Param("food_id") Long foodId);

//    @Query(value = "select * from bill_food where food_id = :id",nativeQuery = true)
//    Optional<Bill_food> findBillFoodByFoodId( @Param("id") Long id);
}