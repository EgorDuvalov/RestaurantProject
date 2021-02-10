package com.innowise.duvalov.dao.impl;

import com.innowise.duvalov.dao.ClientDAO;
import com.innowise.duvalov.entity.Order;
import com.innowise.duvalov.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientDAOImpl extends UserDAOImpl implements ClientDAO {
    private static final String TAKE_MEAL_ID = "select id from menu where name = ?";
    private static final String ADD_MEAL_TO_ORDER = "insert into order_menu value (?,?)";

    private Order order;
    private Connection connection;

    @Override
    public void payTheBill() {
        //make payment
    }

    @Override
    public void addToOrder(Order order, String name) {
        this.order = order;
        this.connection = ConnectionPool.INSTANCE.getConnection();
        writeToTable(takeMealID(name));
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void writeToTable(int mealID) {
        try (PreparedStatement ps = connection.prepareStatement(ADD_MEAL_TO_ORDER)) {
            ps.setInt(1, order.getId());
            ps.setInt(2, mealID);
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private int takeMealID(String name) {
        int id = 0;
        try (PreparedStatement ps = connection.prepareStatement(TAKE_MEAL_ID)) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return id;
    }
}
