package com.example.food_web.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(columnDefinition = "integer default 0")
    private Integer quantity;

    private String imagePath;
    @Column(columnDefinition = "integer default 0")
    private Double price;

    @ManyToOne
    private User user;

    @ManyToOne
    private Category category;

    @Transient
    private Integer quantityBuy;



}
