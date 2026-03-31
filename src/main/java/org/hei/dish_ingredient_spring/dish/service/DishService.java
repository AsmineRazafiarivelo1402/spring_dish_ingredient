package org.hei.dish_ingredient_spring.dish.service;

import org.hei.dish_ingredient_spring.dish.entity.DishEntity;
import org.hei.dish_ingredient_spring.dish.repository.DishRepositroy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class DishService {
    private final DishRepositroy dishRepositroy;
    public DishService(DishRepositroy dishRepositroy){
        this.dishRepositroy = dishRepositroy;
    }
    public List<DishEntity> getDish(){
        return dishRepositroy.findDish();
    }

    public List<DishEntity> getDishes(Double priceUnder, Double priceOver, String name) {
        return dishRepositroy.findDishFiltered(priceUnder, priceOver, name);
    }
}
