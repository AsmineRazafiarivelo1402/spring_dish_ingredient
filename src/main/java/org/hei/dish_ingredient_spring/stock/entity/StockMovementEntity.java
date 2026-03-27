package org.hei.dish_ingredient_spring.stock.entity;

import java.time.Instant;

public class StockMovementEntity {
    int id;
    StockValueEntity value;
    MovementTypeEnum movementtype;
    Instant creationDateTime;
    String commentaire;
}
