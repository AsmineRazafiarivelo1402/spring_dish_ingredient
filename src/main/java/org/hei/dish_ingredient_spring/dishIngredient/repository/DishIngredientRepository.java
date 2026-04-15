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
        String query = "SELECT id FROM dish WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, dishId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean ingredientExists(Integer ingredientId) {
        String query = "SELECT id FROM ingredient WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, ingredientId);
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
    public boolean dishIngredientExists(Connection conn, Integer dishId, Integer ingredientId) {
        String query = "SELECT id FROM dishingredient WHERE id_dish = ? AND id_ingredient = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, dishId);
            ps.setInt(2, ingredientId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void attachDishIngredient(Connection conn, Integer dishId, List<DishIngredientEntity> dishIngredients) {
        if (dishIngredients == null || dishIngredients.isEmpty()) return;

        String insertDishIngredient = """
            INSERT INTO dishingredient (id_dish, id_ingredient, quantity_required, unit)
            VALUES (?, ?, ?, ?::unit_type)
            """;

        try (PreparedStatement ps = conn.prepareStatement(insertDishIngredient)) {
            for (DishIngredientEntity di : dishIngredients) {
                Integer ingredientId = di.getIngredient().getId();

                if (!ingredientExists(ingredientId)) {
                    continue;
                }

                if (dishIngredientExists(conn, dishId, ingredientId)) {
                    continue;
                }

                ps.setInt(1, dishId);
                ps.setInt(2, ingredientId);
                ps.setDouble(3, di.getQuantity_required());
                ps.setString(4, di.getUnit_type().name());
                ps.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}