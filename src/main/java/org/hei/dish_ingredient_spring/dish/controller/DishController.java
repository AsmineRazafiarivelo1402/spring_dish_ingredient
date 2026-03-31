package org.hei.dish_ingredient_spring.dish.controller;

import org.hei.dish_ingredient_spring.dish.entity.DishEntity;
import org.hei.dish_ingredient_spring.dish.service.DishService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DishController {
    private final DishService dishService;
    public DishController(DishService dishService){
        this.dishService = dishService;
    }
    @GetMapping("/dishes")
    public List<DishEntity> getDishes(){
        return dishService.getDish();
    }
    @GetMapping
    public List<DishEntity> getDishes(
            @RequestParam(required = false) Double priceUnder,
            @RequestParam(required = false) Double priceOver,
            @RequestParam(required = false) String name
    ) {
        return dishService.getDishes(priceUnder, priceOver, name);
    }

}
