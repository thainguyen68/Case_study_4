package com.example.food_web.service;

import java.util.List;
import java.util.Optional;

public interface IGenerateService<E, ID> {
    List<E> findAll();

    Optional<E> findOne(ID id);

    void save(E e);

    void delete(ID id);
}
