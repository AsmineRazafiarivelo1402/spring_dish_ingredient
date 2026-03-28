package org.hei.dish_ingredient_spring.ingredient.repository;

import org.hei.dish_ingredient_spring.ingredient.entity.CategoryEnum;
import org.hei.dish_ingredient_spring.ingredient.entity.IngredientEntity;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class IngredientRepository {
    private final DataSource dataSource;
    public IngredientRepository(DataSource dataSource){
        this.dataSource = dataSource;
    }
  public   List<IngredientEntity> findIngredient(){
        List<IngredientEntity> ingredients = new ArrayList<>();
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    """
                            select ingredient.id, ingredient.name, ingredient.price, ingredient.category
                            from ingredient ;
                            """);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                IngredientEntity ingredient = new IngredientEntity();
                ingredient.setId(resultSet.getInt("id"));
                ingredient.setName(resultSet.getString("name"));
                ingredient.setPrice(resultSet.getDouble("price"));
                ingredient.setCategory(CategoryEnum.valueOf(resultSet.getString("category")));
                ingredients.add(ingredient);
            }

            return ingredients;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    }

