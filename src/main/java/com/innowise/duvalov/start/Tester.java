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
        PreparedStatement ps = connection.prepareStatement("select * from users where id = 1");
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            System.out.println(rs.getString("name"));
        }
    }
}
