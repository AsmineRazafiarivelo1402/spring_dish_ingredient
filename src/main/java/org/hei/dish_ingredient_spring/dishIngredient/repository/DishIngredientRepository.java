package org.hei.dish_ingredient_spring.dishIngredient.repository;

import org.hei.dish_ingredient_spring.dishIngredient.entity.DishIngredientEntity;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class DishIngredientRepository {

    private final DataSource dataSource;

    public DishIngredientRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public boolean dishExists(Integer dishId) {
        String query = "SELECT 1 FROM dish WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, dishId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void detachDishIngredient(Connection conn, Integer dishId, List<DishIngredientEntity> dishIngredients) {
        if (dishIngredients == null || dishIngredients.isEmpty()) return;
        String deleteDish = "DELETE FROM dishingredient WHERE id_dish = ?";
        try (PreparedStatement ps = conn.prepareStatement(deleteDish)) {
            ps.setInt(1, dishId);
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveIngredientByDishIngredient(Connection conn, Integer dishId, List<DishIngredientEntity> dishIngredients) {
        if (dishIngredients == null || dishIngredients.isEmpty()) return;

        String insertSql = """
                INSERT INTO ingredient (id, name, price, category)
                VALUES (?, ?, ?, ?::ingredient_category)
                ON CONFLICT (id) DO UPDATE
                SET name = EXCLUDED.name,
                    category = EXCLUDED.category,
                    price = EXCLUDED.price
                """;

        try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
            for (DishIngredientEntity di : dishIngredients) {
                ps.setInt(1, di.getIngredient().getId());
                ps.setString(2, di.getIngredient().getName());
                ps.setDouble(3, di.getIngredient().getPrice());
                ps.setString(4, di.getIngredient().getCategory().name());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void attachDishIngredient(Connection conn, Integer dishId, List<DishIngredientEntity> dishIngredients) {
        if (dishIngredients == null || dishIngredients.isEmpty()) return;

        String insertDishIngredient = """
                INSERT INTO dishingredient (id, id_dish, id_ingredient, quantity_required, unit)
                VALUES (?, ?, ?, ?, ?::unit_type)
                """;

        try (PreparedStatement ps = conn.prepareStatement(insertDishIngredient)) {
            for (DishIngredientEntity di : dishIngredients) {
                ps.setInt(1, di.getId());
                ps.setInt(2, dishId);
                ps.setInt(3, di.getIngredient().getId());
                ps.setDouble(4, di.getQuantity_required());
                ps.setString(5, di.getUnit_tupe().name());
                ps.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}