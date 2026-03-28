package org.hei.dish_ingredient_spring.ingredient.service;

import org.hei.dish_ingredient_spring.ingredient.entity.IngredientEntity;
import org.hei.dish_ingredient_spring.ingredient.repository.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {
    private final IngredientRepository ingredientRepository;

    public IngredientService(IngredientRepository ingredientRepository){
        this.ingredientRepository = ingredientRepository;
    }

    public List<IngredientEntity> getIngredients(){
        return ingredientRepository.findIngredient();
    }
    public IngredientEntity getIngredientById(Integer id){

        return ingredientRepository.findIngredientById(id);
    }
}
