package org.hei.dish_ingredient_spring.stock.entity;

import lombok.*;

import java.time.Instant;
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
@Builder
public class StockMovementEntity {
    int id;
    StockValueEntity value;
    MovementTypeEnum movementtype;
    Instant creationDateTime;
    String commentaire;
}
