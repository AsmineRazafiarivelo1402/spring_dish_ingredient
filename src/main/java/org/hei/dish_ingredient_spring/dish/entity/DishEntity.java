package org.hei.dish_ingredient_spring.dish.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class DishEntity {
    private Integer id;
    private Double selling_price;
    private String name;
    private DishTypeEnum dishType;
    private List<DishIngredientEntity> dishIngredients;
}
