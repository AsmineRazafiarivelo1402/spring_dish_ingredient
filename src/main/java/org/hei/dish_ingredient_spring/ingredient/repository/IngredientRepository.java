package org.hei.dish_ingredient_spring.ingredient.repository;

import org.hei.dish_ingredient_spring.dish.entity.DishIngredientEntity;
import org.hei.dish_ingredient_spring.ingredient.entity.CategoryEnum;
import org.hei.dish_ingredient_spring.ingredient.entity.IngredientEntity;
import org.hei.dish_ingredient_spring.stock.entity.MovementTypeEnum;
import org.hei.dish_ingredient_spring.stock.entity.StockMovementEntity;
import org.hei.dish_ingredient_spring.stock.entity.StockValueEntity;
import org.hei.dish_ingredient_spring.stock.entity.Unit;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.Instant;
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

    public IngredientEntity findIngredientById(Integer id) {

        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    """
                            select ingredient.id, ingredient.name, ingredient.price, ingredient.category
                            from ingredient where id = ?;
                            """);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                IngredientEntity ingredient = new IngredientEntity();
                ingredient.setId(resultSet.getInt("id"));
                ingredient.setName(resultSet.getString("name"));
                ingredient.setPrice(resultSet.getDouble("price"));
                ingredient.setCategory(CategoryEnum.valueOf(resultSet.getString("category")));
                ;
                return ingredient;
            }

       return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public IngredientEntity findIngredientStockAtTimeAndUnit(Integer id, Instant temporal, Unit unit) {
        IngredientEntity ingredient = null;

        try (Connection connection = dataSource.getConnection()) {

            // Récupérer l'ingrédient
            PreparedStatement psIng = connection.prepareStatement(
                    "SELECT id, name, price, category FROM ingredient WHERE id = ?"
            );
            psIng.setInt(1, id);
            ResultSet rsIng = psIng.executeQuery();
            if (rsIng.next()) {
                ingredient = new IngredientEntity();
                ingredient.setId(rsIng.getInt("id"));
                ingredient.setName(rsIng.getString("name"));
                ingredient.setPrice(rsIng.getDouble("price"));
                ingredient.setCategory(CategoryEnum.valueOf(rsIng.getString("category")));
            } else {
                return null; // Ingrédient pas trouvé → géré par le controller
            }

            // Récupérer les mouvements de stock pour cet ingrédient
            PreparedStatement psStock = connection.prepareStatement(
                    "SELECT id, quantity, unit, type, creation_datetime, commentaire " +
                            "FROM stockmovement " +
                            "WHERE id_ingredient = ? AND unit = ?::unit_type AND creation_datetime <= ? " +
                            "ORDER BY creation_datetime ASC"
            );
            psStock.setInt(1, id);
            psStock.setString(2, unit.name());
            psStock.setTimestamp(3, java.sql.Timestamp.from(temporal));
            ResultSet rsStock = psStock.executeQuery();

            List<StockMovementEntity> stockList = new ArrayList<>();
            while (rsStock.next()) {
                StockMovementEntity sm = new StockMovementEntity();
                sm.setId(rsStock.getInt("id"));
                sm.setMovementtype(MovementTypeEnum.valueOf(rsStock.getString("type")));
                sm.setCreationDateTime(rsStock.getTimestamp("creation_datetime").toInstant());
                sm.setCommentaire(rsStock.getString("commentaire"));

                StockValueEntity value = new StockValueEntity();
                value.setQuantity(rsStock.getDouble("quantity"));
                value.setUnit(Unit.valueOf(rsStock.getString("unit")));
                sm.setValue(value);

                stockList.add(sm);
            }

            ingredient.setStockMovementList(stockList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ingredient;
    }

}


