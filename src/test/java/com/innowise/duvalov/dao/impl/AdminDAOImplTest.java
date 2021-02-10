package com.innowise.duvalov.dao.impl;

import com.innowise.duvalov.entity.Order;
import com.innowise.duvalov.pool.ConnectionPool;
import org.junit.Assert;
import org.junit.Test;

public class AdminDAOImplTest {
    @Test
    public void testHandling() {
        ConnectionPool.INSTANCE.openPool();
        Order order = new OrderDAOImpl().orderFromDB(1);
        AdminDAOImpl admin = new AdminDAOImpl();
        admin.handleOrder(order);
        Assert.assertEquals(63.2, order.getBill(), 0.0);
        ConnectionPool.INSTANCE.closePool();
    }
}
