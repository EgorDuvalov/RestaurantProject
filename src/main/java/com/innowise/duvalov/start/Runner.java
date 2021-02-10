package com.innowise.duvalov.start;

import com.innowise.duvalov.dao.impl.UserDAOImpl;
import com.innowise.duvalov.entity.User;
import com.innowise.duvalov.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Runner {
    private static final String TAKE_ALL_USERS = "select * from users";

    public static void main(String[] args) {
        ConnectionPool.INSTANCE.openPool();
        Connection connection = ConnectionPool.INSTANCE.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(TAKE_ALL_USERS)) {
            User user = new UserDAOImpl().getUser(1);
            System.out.println(user);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
