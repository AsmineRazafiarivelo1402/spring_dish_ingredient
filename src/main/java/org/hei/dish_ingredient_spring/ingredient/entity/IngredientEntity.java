package org.hei.dish_ingredient_spring.ingredient.entity;

import lombok.*;
import org.hei.dish_ingredient_spring.stock.entity.StockMovementEntity;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
@Builder
public class IngredientEntity {
    private Integer id;
    private String name;
    private CategoryEnum category;
    private Double price;
    private List<StockMovementEntity> stockMovementList;
}
