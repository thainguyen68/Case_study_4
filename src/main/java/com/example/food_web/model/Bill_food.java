package com.example.food_web.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Bill_food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;

    @ManyToOne
    private Bill bill;
    @ManyToOne
    private Food food;

}
