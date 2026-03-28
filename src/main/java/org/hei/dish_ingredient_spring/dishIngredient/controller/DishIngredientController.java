package org.hei.dish_ingredient_spring.dishIngredient.controller;

import org.hei.dish_ingredient_spring.dishIngredient.entity.DishIngredientEntity;
import org.hei.dish_ingredient_spring.dishIngredient.service.DishIngredientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dishes")
public class DishIngredientController {


  private final DishIngredientService dishIngredientService;

    public DishIngredientController(DishIngredientService dishIngredientService) {
        this.dishIngredientService = dishIngredientService;
    }

    @PutMapping("/{id}/ingredients")
    public ResponseEntity<?> updateDishIngredients(
            @PathVariable Integer id,
            @RequestBody List<DishIngredientEntity> dishIngredients) {

        if (dishIngredients == null || dishIngredients.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Request body must contain ingredients list.");
        }

        boolean updated = dishIngredientService.updateDishIngredients(id, dishIngredients);

        if (!updated) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Dish.id=" + id + " is not found");
        }

        return ResponseEntity.ok("Dish ingredients updated successfully.");
    }
}