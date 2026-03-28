package org.hei.dish_ingredient_spring.ingredient.controller;

import org.hei.dish_ingredient_spring.ingredient.entity.IngredientEntity;
import org.hei.dish_ingredient_spring.ingredient.service.IngredientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IngredientController {
    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService){
        this.ingredientService = ingredientService;
    }

    @GetMapping("/ingredients")
    public List<IngredientEntity> getIngredients(){
        return ingredientService.getIngredients();
    }
    @GetMapping("/ingredient/{id}")
    public ResponseEntity<?>  getIngredientById(@PathVariable Integer id){
        IngredientEntity ingredient = ingredientService.getIngredientById(id);

        if (ingredient != null) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ingredient);
        } else {
            String message = "Ingredient.id={" + id + "} is not found";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

    }
}
