package org.hei.dish_ingredient_spring.dish.repository;

import org.hei.dish_ingredient_spring.dish.entity.DishEntity;
import org.hei.dish_ingredient_spring.dishIngredient.entity.DishIngredientEntity;
import org.hei.dish_ingredient_spring.dish.entity.DishTypeEnum;
import org.hei.dish_ingredient_spring.ingredient.entity.CategoryEnum;
import org.hei.dish_ingredient_spring.ingredient.entity.IngredientEntity;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DishRepositroy {
    private final DataSource dataSource;
    public DishRepositroy(DataSource dataSource){
        this.dataSource = dataSource;
    }
    private List<DishIngredientEntity> findDishIngredientByDishId (){



        List<DishIngredientEntity> ingredients = new ArrayList<>();
        try {  Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    """
                    
                            select id_ingredient, ingredient.id, ingredient.name, ingredient.price,ingredient.category from DishIngredient join dish on dish.id= id_dish join ingredient on ingredient.id= id_ingredient  
                                                         

                       """);

            ResultSet resultSet =
                    preparedStatement.executeQuery();
            while (resultSet.next()) {
                DishIngredientEntity
                        dishingredient = new DishIngredientEntity();
                IngredientEntity ingredient = new IngredientEntity();

                ingredient.setId(resultSet.getInt("id_ingredient"));
                ingredient.setName(resultSet.getString("name"));
                ingredient.setPrice(resultSet.getDouble("price"));

                ingredient.setCategory(
                        CategoryEnum.valueOf(resultSet.getString(
                                "category")));

                dishingredient.
                        setIngredient(ingredient);
                ingredients.add(dishingredient);

            }

            return ingredients;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
   public List<DishEntity> findDish() {
List<DishEntity> listDish = new ArrayList<>();

        try {   Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    """
                            select dish.id as dish_id, dish.name as dish_name, dish_type, dish.selling_price as dish_price
                            from dish
                            ;
                            """);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                DishEntity dish = new DishEntity();
                dish.setId(resultSet.getInt("dish_id"));
                dish.setName(resultSet.getString("dish_name"));
                dish.setDishType(DishTypeEnum.valueOf(resultSet.getString("dish_type")));
                dish.setSelling_price(resultSet.getObject("dish_price") == null
                        ? null : resultSet.getDouble("dish_price"));
                dish.setDishIngredients(findDishIngredientByDishId());
                listDish.add(dish);

            }
            return listDish;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



        public List<DishEntity> findDishFiltered(Double priceUnder, Double priceOver, String name) {

            List<DishEntity> listDish = new ArrayList<>();

            try {
                Connection connection = dataSource.getConnection();

                String sql = """
                    select dish.id as dish_id,
                           dish.name as dish_name,
                           dish.dish_type,
                           dish.selling_price as dish_price
                    from dish
                    where 1=1
                    """;

                if (priceUnder != null) {
                    sql += " AND selling_price < ?";
                }

                if (priceOver != null) {
                    sql += " AND selling_price > ?";
                }

                if (name != null) {
                    sql += " AND name LIKE ?";
                }

                PreparedStatement preparedStatement = connection.prepareStatement(sql);

                int index = 1;

                if (priceUnder != null) {
                    preparedStatement.setDouble(index++, priceUnder);
                }

                if (priceOver != null) {
                    preparedStatement.setDouble(index++, priceOver);
                }

                if (name != null) {
                    preparedStatement.setString(index++, "%" + name + "%");
                }

                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    DishEntity dish = new DishEntity();

                    dish.setId(resultSet.getInt("dish_id"));
                    dish.setName(resultSet.getString("dish_name"));
                    dish.setDishType(
                            DishTypeEnum.valueOf(resultSet.getString("dish_type"))
                    );

                    dish.setSelling_price(
                            resultSet.getObject("dish_price") == null
                                    ? null
                                    : resultSet.getDouble("dish_price")
                    );

                    listDish.add(dish);
                }

                return listDish;

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }


