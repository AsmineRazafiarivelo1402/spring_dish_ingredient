package org.hei.dish_ingredient_spring.dish.entity;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Setter
@Getter
@Builder
public class DishEntity {
    private Integer id;
    private Double selling_price;
    private String name;
    private DishTypeEnum dishType;
    private List<DishIngredientEntity> dishIngredients;
}
