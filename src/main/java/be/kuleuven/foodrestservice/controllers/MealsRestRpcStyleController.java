package be.kuleuven.foodrestservice.controllers;

import be.kuleuven.foodrestservice.domain.Meal;
import be.kuleuven.foodrestservice.domain.MealsRepository;
import be.kuleuven.foodrestservice.exceptions.MealNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/restrpc/meals")
    @ResponseBody
    void addMeal(@RequestBody Meal meal) {
        mealsRepository.addMeal(meal);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/restrpc/meals/{id}")
    void deleteMeal(@PathVariable String id) {
        mealsRepository.deleteMeal(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/restrpc/meals/{id}")
    @ResponseBody
    void updateMeal(@RequestBody Meal meal) {
        mealsRepository.updateMeal(meal);
    }
}