package com.adabrain.myweb.models;

import org.springframework.data.repository.CrudRepository;

import java.util.stream.Stream;

public interface CoffeeRepository extends CrudRepository<Coffee, String> {
    Stream<Coffee> findCoffeeByName(String name);
}
