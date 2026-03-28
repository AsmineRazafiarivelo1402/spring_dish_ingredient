package org.hei.dish_ingredient_spring.stock.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
@Builder
public class StockValueEntity {
    Double  quantity;
    Unit unit;
}
