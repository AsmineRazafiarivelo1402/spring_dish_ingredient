package org.hei.dish_ingredient_spring.dishIngredient.service;


import org.hei.dish_ingredient_spring.dishIngredient.entity.DishIngredientEntity;
import org.hei.dish_ingredient_spring.dishIngredient.repository.DishIngredientRepository;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.List;

@Service
public class DishIngredientService {

    private final DishIngredientRepository dishIngredientRepository;

    public DishIngredientService(DishIngredientRepository dishIngredientRepository) {
        this.dishIngredientRepository = dishIngredientRepository;
    }

    public boolean updateDishIngredients(Integer dishId, List<DishIngredientEntity> dishIngredients) {
        if (dishIngredients == null || dishIngredients.isEmpty()) {
            throw new IllegalArgumentException("Request body must contain ingredients list.");
        }

        try (Connection conn = dishIngredientRepository.getConnection()) {
            if (!dishIngredientRepository.dishExists(dishId)) {
                return false;
            }

            dishIngredientRepository.detachDishIngredient(conn, dishId, dishIngredients);



            // Ajoute les associations au plat
            dishIngredientRepository.attachDishIngredient(conn, dishId, dishIngredients);

            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
