package com.innowise.duvalov.dao.impl;

import com.innowise.duvalov.entity.User;
import com.innowise.duvalov.pool.ConnectionPool;
import org.junit.Assert;
import org.junit.Test;

public class UserDAOImplTest {
    @Test
    public void testAddAndGetUser() {
        ConnectionPool.INSTANCE.openPool();
        User expectedUser = createUser(1, "John", "Client");
        User returnedUser = new UserDAOImpl().getUser(1);
        Assert.assertEquals(expectedUser, returnedUser);
        ConnectionPool.INSTANCE.closePool();
    }

    public User createUser(int id, String name, String role) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setRole(role);
        return user;
    }

}