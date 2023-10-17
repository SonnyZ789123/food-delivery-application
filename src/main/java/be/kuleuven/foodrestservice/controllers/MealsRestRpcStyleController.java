package be.kuleuven.foodrestservice.controllers;

import be.kuleuven.foodrestservice.domain.Meal;
import be.kuleuven.foodrestservice.domain.MealsRepository;
import be.kuleuven.foodrestservice.exceptions.MealNotFoundException;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@RestController
public class MealsRestRpcStyleController {

    private final MealsRepository mealsRepository;

    @Autowired
    MealsRestRpcStyleController(MealsRepository mealsRepository) {
        this.mealsRepository = mealsRepository;
    }

    @GetMapping("/restrpc/meals/{id}")
    Meal getMealById(@PathVariable String id) {
        Optional<Meal> meal = mealsRepository.findMeal(id);

        return meal.orElseThrow(() -> new MealNotFoundException(id));
    }

    @GetMapping("/restrpc/meals")
    Collection<Meal> getMeals() {
        return mealsRepository.getAllMeal();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/restrpc/cheapest-meal")
    Meal getCheapestMeal() {
        return mealsRepository.getCheapestMeal().orElseThrow(MealNotFoundException::new);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/restrpc/largest meal")
    Meal getLargestMeal() {
        return mealsRepository.getLargestMeal().orElseThrow(MealNotFoundException::new);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/restrpc/meals")
    void addMeal(@RequestBody Meal meal) {
        meal.setId(UUID.randomUUID().toString());
        mealsRepository.addMeal(meal);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/restrpc/meals/{id}")
    void deleteMeal(@PathVariable String id) {
        mealsRepository.deleteMeal(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/restrpc/meals/{id}")
    void updateMeal(@PathVariable String id, @RequestBody Meal meal) {
        mealsRepository.updateMeal(id, meal);
    }
}