package com.innowise.duvalov.dao.impl;

import com.innowise.duvalov.dao.UserDAO;
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

    private static final String ADD_USER = "insert into users values (?,?,?)";
    private static final String GET_USER = "select * from users where id = ?";

    @Override
    //TODO synchronization between id in db and in obj
    public void setUser(User user) {
        Connection connection = ConnectionPool.INSTANCE.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(ADD_USER)) {
            ps.setInt(1, user.getId());
            ps.setString(2, user.getName());
            ps.setString(3, user.getRole());
            ps.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            LOGGER.error(e);
        }
    }

    @Override
    public User getUser(int id) {
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

    public User initializeUser(ResultSet rs) {
        User user = new User();
        try {
            while (rs.next()) {
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setRole(rs.getString("role"));
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return user;
    }
}
