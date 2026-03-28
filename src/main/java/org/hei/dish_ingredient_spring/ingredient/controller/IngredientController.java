package org.hei.dish_ingredient_spring.ingredient.controller;

import org.hei.dish_ingredient_spring.ingredient.entity.IngredientEntity;
import org.hei.dish_ingredient_spring.ingredient.service.IngredientService;
import org.hei.dish_ingredient_spring.stock.entity.Unit;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
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
    @GetMapping("/ingredients/{id}")
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
    @GetMapping("/ingredients/{id}/stock")
    public ResponseEntity<?> getIngredientStockAtTimeAndUnit(
            @PathVariable Integer id,
            @RequestParam(required = false) Instant at,
            @RequestParam(required = false) Unit unit) {

    if(at == null || unit == null){
        String message = "Either mandatory query parameter `at` or\n" +
                "`unit` is not provided.";
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }
        IngredientEntity ingredient = ingredientService.getIngredientStockAtTimeAndUnit(id, at, unit);
        if (ingredient == null) {
            String message = "Ingredient.id={" + id + "} is not found";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.status(HttpStatus.OK).body(ingredient);
    }
}
