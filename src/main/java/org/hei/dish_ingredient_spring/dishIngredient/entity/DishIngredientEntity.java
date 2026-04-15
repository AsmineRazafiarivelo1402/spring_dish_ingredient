package org.hei.dish_ingredient_spring.dishIngredient.entity;

import lombok.*;
import org.hei.dish_ingredient_spring.dish.entity.DishEntity;
import org.hei.dish_ingredient_spring.ingredient.entity.IngredientEntity;
import org.hei.dish_ingredient_spring.stock.entity.Unit;

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
    private double quantity_required;
    private Unit unit_type;
}
