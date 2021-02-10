package com.innowise.duvalov.dao.impl;

import com.innowise.duvalov.dao.MealDAO;
import com.innowise.duvalov.entity.Meal;
import com.innowise.duvalov.pool.ConnectionPool;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MealDAOImpl implements MealDAO {
    private static final Logger LOGGER = Logger.getLogger(UserDAOImpl.class);


    private static final String ADD_MEAL = "insert into menu values (?,?,?)";
    private static final String GET_MEAL = "select * from menu where id = ?";


    @Override
    public void setMeal(Meal meal) {
        Connection connection = ConnectionPool.INSTANCE.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(ADD_MEAL)) {
            ps.setInt(1, meal.getId());
            ps.setString(2, meal.getName());
            ps.setDouble(3, meal.getPrice());
            ps.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            LOGGER.error(e);
        }
    }

    @Override
    public Meal getMeal(int id) {
        Connection connection = ConnectionPool.INSTANCE.getConnection();
        Meal meal = null;
        try (PreparedStatement ps = connection.prepareStatement(GET_MEAL)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            meal = initializeMeal(rs);
            connection.close();
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return meal;
    }

    public Meal initializeMeal(ResultSet rs) {
        Meal meal = new Meal();
        try {
            while (rs.next()) {
                meal.setId(rs.getInt("id"));
                meal.setName(rs.getString("name"));
                meal.setPrice(rs.getDouble("price"));
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return meal;
    }
}
