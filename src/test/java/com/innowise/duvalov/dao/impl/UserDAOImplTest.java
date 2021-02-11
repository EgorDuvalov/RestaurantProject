package com.innowise.duvalov.dao.impl;

import com.innowise.duvalov.entity.Order;
import com.innowise.duvalov.entity.Role;
import com.innowise.duvalov.entity.User;
import com.innowise.duvalov.pool.ConnectionPool;
import org.junit.Assert;
import org.junit.Test;

public class UserDAOImplTest {
    @Test
    public void testAddAndGetUser() {
        ConnectionPool.INSTANCE.openPool();
        User expectedUser = createUser(1, "John", Role.Client);
        User returnedUser = new UserDAOImpl().userFromDB(1);
        Assert.assertEquals(expectedUser, returnedUser);
        ConnectionPool.INSTANCE.closePool();
    }

    @Test
    public void testHandling() {
        ConnectionPool.INSTANCE.openPool();
        Order order = new OrderDAOImpl().orderFromDB(1);
        UserDAOImpl admin = new UserDAOImpl();
        admin.handleOrder(order);
        Assert.assertEquals(63.2, order.getBill(), 0.0);
        ConnectionPool.INSTANCE.closePool();
    }

    public User createUser(int id, String name, Role role) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setRole(role);
        return user;
    }

}