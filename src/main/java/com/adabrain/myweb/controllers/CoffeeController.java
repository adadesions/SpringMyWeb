package com.adabrain.myweb.controllers;

import com.adabrain.myweb.models.Coffee;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class CoffeeController {
    List<Coffee> store = new ArrayList<>();

    public CoffeeController() {
        store.addAll(List.of(
                new Coffee("Americano"),
                new Coffee("Es-Yen"),
                new Coffee("Espresso")
        ));
    }

    @GetMapping("/coffees")
    Iterable<Coffee> getCoffees() {
        return store;
    }

    @GetMapping("/coffees/{id}")
    Optional<Coffee> getCoffeeById(@PathVariable String id) {
        for (Coffee c : store) {
            if (c.getId().equals(id)) {
                return Optional.of(c);
            }
        }

        return Optional.empty();
    }

    @GetMapping("/coffees/name/{name}")
    Optional<Coffee> getCoffeeByName(@PathVariable String name) {
        for (Coffee c : store) {
            if (c.getName().equals(name)) {
                return Optional.of(c);
            }
        }

        return Optional.empty();
    }


}
