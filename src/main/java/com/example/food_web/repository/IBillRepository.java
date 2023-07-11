package com.example.food_web.repository;

import com.example.food_web.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IBillRepository extends JpaRepository<Bill, Long> {
    @Query(value = "select * from bill_food join bill b on b.id = bill_food.bill_id where food_id = :id", nativeQuery = true)
    List<Bill> findAllBillByFood_id (@Param("id") Long id);

    @Query(value = "delete from bill_food where food_id = :idDelete", nativeQuery = true)
    void deleteBill_food(@Param("idDelete") Long id);

//    @Query(value = "select * from bill where user_id = :id", nativeQuery = true)
//    Optional<Bill> findBillByUserId(@Param("id") Long userId);
    @Query(value = "select * from bill where user_id = :id", nativeQuery = true)
    List<Bill> findBillByUserId(@Param("id") Long userId);

    @Query(value = "select count(food_id) from bill_food where bill_id = :id", nativeQuery = true)
    Integer totalQuantity(@Param("id") Long id);
}
