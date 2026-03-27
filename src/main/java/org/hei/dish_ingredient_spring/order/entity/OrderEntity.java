package org.hei.dish_ingredient_spring.order.entity;

import org.hei.dish_ingredient_spring.dish.entity.DishOrderEntity;

import java.time.Instant;
import java.util.List;

public class OrderEntity {
    private  Integer id;
    private String reference;
    private Instant creationDatetime;
    private List<DishOrderEntity> dishOrders;
    private OrderTypeEnum orderType;
    private OrderStatusEnum orderStatus;
}
