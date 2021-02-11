package com.innowise.duvalov.dao.impl;

import com.innowise.duvalov.dao.UserDAO;
import com.innowise.duvalov.entity.Order;
import com.innowise.duvalov.entity.Role;
import com.innowise.duvalov.entity.User;
import com.innowise.duvalov.pool.ConnectionPool;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    private static final Logger LOGGER = Logger.getLogger(UserDAOImpl.class);

    private static final String ADD_USER = "insert into users (name, role) values (?,?)";
    private static final String GET_USER = "select * from users where id = ?";
    private static final String TAKE_ORDERED_MEALS = "SELECT menu_id from order_menu where order_id=?";
    private static final String TAKE_MEAL_PRICE = "SELECT price from menu where id=?";
    private static final String TAKE_MEAL_ID = "select id from menu where name = ?";
    private static final String ADD_MEAL_TO_ORDER = "insert into order_menu value (?,?)";
    private static final double MAX_BILL = 100;

    private Order order;
    private Connection connection;

    @Override
    //TODO synchronization between id in db and in obj
    public void userToDB(User user) {
        Connection connection = ConnectionPool.INSTANCE.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(ADD_USER)) {
            ps.setString(1, user.getName());
            ps.setInt(2, user.getRole().getRoleNumber());
            ps.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            LOGGER.error(e);
        }
    }

    @Override
    public User userFromDB(int id) {
        Connection connection = ConnectionPool.INSTANCE.getConnection();
        User user = null;
        try (PreparedStatement ps = connection.prepareStatement(GET_USER)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            user = initializeUser(rs);
            connection.close();
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return user;
    }

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

    public User initializeUser(ResultSet rs) {
        User user = new User();
        try {
            while (rs.next()) {
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                if ((rs.getInt("role")) == 0) {
                    user.setRole(Role.Client);
                } else {
                    user.setRole(Role.Admin);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return user;
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
}
