package com.innowise.duvalov.start;

import com.innowise.duvalov.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Tester {
    public static void main(String[] args) throws SQLException {
        ConnectionPool.INSTANCE.openPool();
        Connection connection = ConnectionPool.INSTANCE.getConnection();
        PreparedStatement ps = connection.prepareStatement("select id from users where name = ?");
        String name = "John";
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            System.out.println(rs.getInt("id"));
        }
    }
}
