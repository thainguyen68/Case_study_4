package com.example.food_web.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "integer default 0")
    private Long quantity;

    @Column(columnDefinition = "boolean default false")
    private Boolean status;

    @ManyToOne
    private User user;

    @ManyToMany
    private List<Food> food;




}
