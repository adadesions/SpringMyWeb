package com.adabrain.myweb.controllers;

import com.adabrain.myweb.models.Coffee;
import com.adabrain.myweb.models.CoffeeRepository;
import lombok.extern.log4j.Log4j2;
import org.apache.el.stream.Stream;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@RestController
@Log4j2

@RequestMapping("/coffees")
public class CoffeeController {
    private final CoffeeRepository coffeeRepository;

    public CoffeeController(CoffeeRepository coffeeRepository) {
        this.coffeeRepository = coffeeRepository;
        
        this.coffeeRepository.saveAll(List.of(
                new Coffee("Americano"),
                new Coffee("Es-Yen"),
                new Coffee("Espresso")
        ));
    }

    @GetMapping
    Iterable<Coffee> getCoffees() {
        log.debug("Someone get all coffee.");
        return coffeeRepository.findAll();
    }

    @GetMapping("/{id}")
    Optional<Coffee> getCoffeeById(@PathVariable String id) {
        return coffeeRepository.findById(id);
    }

    @GetMapping("/name/{name}")
    Optional<Coffee> getCoffeeByName(@PathVariable String name) {
        for (Coffee c : coffeeRepository.findAll()) {
            if (c.getName().equals(name)) {
                return Optional.of(c);
            }
        }

        return Optional.empty();
    }

    @GetMapping("/search")
    Optional<Coffee> getCoffeeBySearch(@RequestParam String name) {
        return StreamSupport
                .stream(coffeeRepository.findAll().spliterator(), false)
                .filter(coffee -> coffee.getName().equals(name))
                .findFirst();
    }

    @PostMapping
    Coffee postCoffee(@RequestBody Coffee coffee) {
        coffeeRepository.save(coffee);
        log.trace("[POST] create new coffee" + coffee.info());
        return coffee;
    }

    @PutMapping("/{id}")
    ResponseEntity<Coffee> putCoffee(@PathVariable String id, @RequestBody Coffee coffee) {
        return (!coffeeRepository.existsById(id))
                ? new ResponseEntity<>(coffeeRepository.save(coffee), HttpStatus.CREATED)
                : new ResponseEntity<>(coffeeRepository.save(coffee), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteCoffee(@PathVariable String id) {
        coffeeRepository.deleteById(id);
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }
}
