package com.innowise.duvalov.dao.impl;

import com.innowise.duvalov.dao.OrderDAO;
import com.innowise.duvalov.entity.Order;
import com.innowise.duvalov.entity.User;
import com.innowise.duvalov.pool.ConnectionPool;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDAOImpl implements OrderDAO {
    private static final Logger LOGGER = Logger.getLogger(UserDAOImpl.class);

    private static final String addOrder = "insert into orders values (?,?,?,?,?)";
    private static final String GET_ORDER = "select * from orders where id = ?";

    @Override
    public void setOrder(Order order) {
        Connection connection = ConnectionPool.INSTANCE.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(addOrder)) {
            ps.setInt(1, order.getId());
            ps.setBoolean(2, order.getStatus());
            ps.setInt(3, order.getUserId());
            ps.setDouble(4, order.getBill());
            ps.setTime(5, order.getTime());
            ps.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            LOGGER.error(e);
        }
    }

    @Override
    public Order getOrder(int id) {
        Connection connection = ConnectionPool.INSTANCE.getConnection();
        Order order = null;
        try (PreparedStatement ps = connection.prepareStatement(GET_ORDER)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            order = initializeOrder(rs);
            connection.close();
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return order;
    }

    public Order initializeOrder(ResultSet rs) {
        Order order = new Order();
        try {
            while (rs.next()) {
                order.setId(rs.getInt("id"));
                order.setBill(rs.getDouble("bill"));
                order.setStatus(rs.getBoolean("status"));
                order.setUserId(rs.getInt("user_id"));
                order.setTime(rs.getTime("time"));
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return order;
    }
}
