package org.hei.dish_ingredient_spring.dish.service;

import org.hei.dish_ingredient_spring.dish.entity.DishEntity;
import org.hei.dish_ingredient_spring.dish.repository.DishRepositroy;
import org.springframework.stereotype.Service;

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

}
