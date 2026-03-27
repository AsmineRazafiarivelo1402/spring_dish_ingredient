package org.hei.dish_ingredient_spring.dish.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hei.dish_ingredient_spring.ingredient.entity.IngredientEntity;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class DishIngredientEntity {
    private Integer id;
    private DishEntity dish;
    private IngredientEntity ingredient;
}
