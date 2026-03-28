package org.hei.dish_ingredient_spring.dish.entity;

import lombok.*;
import org.hei.dish_ingredient_spring.ingredient.entity.IngredientEntity;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
@Builder
public class DishIngredientEntity {
    private Integer id;
    private DishEntity dish;
    private IngredientEntity ingredient;
}
