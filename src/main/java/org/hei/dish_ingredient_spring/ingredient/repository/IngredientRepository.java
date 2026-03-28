package org.hei.dish_ingredient_spring.ingredient.repository;

import org.hei.dish_ingredient_spring.dish.entity.DishIngredientEntity;
import org.hei.dish_ingredient_spring.ingredient.entity.CategoryEnum;
import org.hei.dish_ingredient_spring.ingredient.entity.IngredientEntity;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
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
//    public List<IngredientEntity> findIngredientByid(Integer id){
//        {
//
//            List<DishIngredientEntity> dishIngredientList = new ArrayList<>();
//
//            String sql = """
//        SELECT
//            id_ingredient,
//            id_dish,
//            dish.name AS dish_name,
//            ingredient.id AS idIngredient,
//            ingredient.name AS ingredient_name,
//            ingredient.price,
//            ingredient.category
//        FROM DishIngredient
//        JOIN dish ON dish.id = id_dish
//        JOIN ingredient ON ingredient.id = id_ingredient
//        WHERE
//            (? IS NULL OR ingredient.name ILIKE ?)
//        AND
//            (?::ingredient_category IS NULL OR ingredient.category = ?::ingredient_category)
//        AND
//            (? IS NULL OR dish.name ILIKE ?)
//        LIMIT ? OFFSET ?
//    """;
//
//            try (Connection connection = dataSource.getConnection();
//                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//
//                // 1 & 2 → ingredientName
//                if (ingredientName == null) {
//                    preparedStatement.setNull(1, Types.VARCHAR);
//                    preparedStatement.setNull(2, Types.VARCHAR);
//                } else {
//                    preparedStatement.setString(1, ingredientName);
//                    preparedStatement.setString(2, "%" + ingredientName + "%");
//                }
//
//                // 3 & 4 → category (ENUM PostgreSQL)
//                if (category == null) {
//                    preparedStatement.setNull(3, Types.OTHER); // important pour ENUM
//                    preparedStatement.setNull(4, Types.OTHER);
//                } else {
//                    preparedStatement.setString(3, category.name());
//                    preparedStatement.setString(4, category.name());
//                }
//
//                // 5 & 6 → dishName
//                if (dishName == null) {
//                    preparedStatement.setNull(5, Types.VARCHAR);
//                    preparedStatement.setNull(6, Types.VARCHAR);
//                } else {
//                    preparedStatement.setString(5, dishName);
//                    preparedStatement.setString(6, "%" + dishName + "%");
//                }
//
//                // 7 → LIMIT
//                preparedStatement.setInt(7, size);
//
//                // 8 → OFFSET
//                preparedStatement.setInt(8, (page - 1) * size);
//
//                // ---------- EXECUTION ----------
//                try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                    while (resultSet.next()) {
//                        // Ingredient
//                        Ingredient ingredient = new Ingredient();
//                        ingredient.setId(resultSet.getInt("idIngredient"));
//                        ingredient.setName(resultSet.getString("ingredient_name"));
//                        ingredient.setPrice(resultSet.getDouble("price"));
//                        ingredient.setCategory(
//                                CategoryEnum.valueOf(resultSet.getString("category"))
//                        );
//
//                        // Dish
//                        Dish dish = new Dish();
//                        dish.setId(resultSet.getInt("id_dish"));
//                        dish.setName(resultSet.getString("dish_name"));
//
//                        // DishIngredient
//                        DishIngredient dishIngredient = new DishIngredient();
//                        dishIngredient.setIngredient(ingredient);
//                        dishIngredient.setDish(dish);
//
//                        dishIngredientList.add(dishIngredient);
//                    }
//                }
//
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//
//            return dishIngredientList;
//        }
//    }
}


