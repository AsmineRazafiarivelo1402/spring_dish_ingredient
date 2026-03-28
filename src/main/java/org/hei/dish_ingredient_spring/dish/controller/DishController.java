package org.hei.dish_ingredient_spring.dish.controller;

import org.hei.dish_ingredient_spring.dish.entity.DishEntity;
import org.hei.dish_ingredient_spring.dish.service.DishService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
