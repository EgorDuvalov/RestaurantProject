package com.innowise.duvalov.dao.impl;

import com.innowise.duvalov.dao.AdminDAO;
import com.innowise.duvalov.entity.Order;
import com.innowise.duvalov.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class AdminDAOImpl extends UserDAOImpl implements AdminDAO {
    private static final String TAKE_ORDERED_MEALS = "SELECT menu_id from order_menu where order_id=?";
    private static final String TAKE_MEAL_PRICE = "SELECT price from menu where id=?";
    private static final double MAX_BILL = 100;

    private Order order;
    private Connection connection;

    @Override
    public void handleOrder(Order order) {
        this.order = order;
        this.connection = ConnectionPool.INSTANCE.getConnection();
        calculateBill();
        confirmOrder();
        try {
            this.connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void confirmOrder() {
        order.setStatus(order.getBill() < MAX_BILL);
        //sending to kitchen
    }

    private void calculateBill() {
        double sum = 0;
        for (int mealID : takeMealsID()) {
            sum += takeMealPrice(mealID);
        }
        order.setBill(sum);
    }

    private double takeMealPrice(int id) {
        double price = 0;
        try (PreparedStatement ps = connection.prepareStatement(TAKE_MEAL_PRICE)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                price = rs.getDouble("price");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return price;
    }

    private List<Integer> takeMealsID() {
        List<Integer> orderedMeals = new LinkedList<>();
        try (PreparedStatement ps = connection.prepareStatement(TAKE_ORDERED_MEALS)) {
            ps.setInt(1, order.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                orderedMeals.add(rs.getInt("menu_id"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return orderedMeals;
    }
}

