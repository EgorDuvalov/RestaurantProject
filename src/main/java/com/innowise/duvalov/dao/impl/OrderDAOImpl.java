package com.innowise.duvalov.dao.impl;

import com.innowise.duvalov.dao.OrderDAO;
import com.innowise.duvalov.entity.Order;
import com.innowise.duvalov.pool.ConnectionPool;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDAOImpl implements OrderDAO {
    private static final Logger LOGGER = Logger.getLogger(UserDAOImpl.class);

    private static final String addOrder = "insert into orders(status, client_id, bill, time) values (?,?,?,?)";
    private static final String GET_ORDER = "select * from orders where id = ?";

    @Override
    public void orderToDB(Order order) {
        Connection connection = ConnectionPool.INSTANCE.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(addOrder)) {
            ps.setBoolean(1, order.isStatus());
            ps.setInt(2, order.getUserId());
            ps.setDouble(3, order.getBill());
            ps.setTime(4, order.getTime());
            ps.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            LOGGER.error(e);
        }
    }

    @Override
    public Order orderFromDB(int id) {
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
                order.setUserId(rs.getInt("client_id"));
                order.setTime(rs.getTime("time"));
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return order;
    }
}
