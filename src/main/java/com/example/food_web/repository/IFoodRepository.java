package com.example.food_web.repository;

import com.example.food_web.model.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IFoodRepository extends JpaRepository<Food, Long> {
    @Query(value = "Select * from food inner join category  on food.category_id = category.id  where food.name like :name or category.name like :name", nativeQuery = true)
    Page<Food> findProductByNameAbout(@Param("name") String name, Pageable pageable);

    @Query(value = "select * from Food fo join Category c on fo.category_id = c.id where (fo.name like :name and fo.price between :min and :max) or (c.name like :name and fo.price between :min and :max)", nativeQuery = true)
    Page<Food> filter(@Param("min") Long minPrice,
                       @Param("max") Long maxPrice,
                       @Param("name") String name,
                       Pageable pageable);

    Page<Food> findAllByOrderByPriceAsc(Pageable pageable);
    Page<Food> findAllByOrderByPriceDesc(Pageable pageable);
}
