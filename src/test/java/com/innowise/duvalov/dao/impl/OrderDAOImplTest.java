package com.innowise.duvalov.dao.impl;

import com.innowise.duvalov.entity.Order;
import com.innowise.duvalov.pool.ConnectionPool;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.sql.Time;

public class OrderDAOImplTest {
    @Test
    public void testAddAndGetOrder() {
        ConnectionPool.INSTANCE.openPool();
        Time time = new Time(0, 0,0);
        Order expectedOrder = createOrder(1, 0, 1, false, time);
        Order returnedOrder = new OrderDAOImpl().orderFromDB(1);
        ConnectionPool.INSTANCE.closePool();
        Assert.assertEquals(expectedOrder, returnedOrder);
    }

    @Test
    public void trySomeTests(){
        for (int i = 0; i < 30; i++) {
            testAddAndGetOrder();
        }
    }

    public Order createOrder(int id, double bill, int userId,
                             boolean status, Time time) {
        Order order = new Order();
        order.setId(id);
        order.setBill(bill);
        order.setStatus(status);
        order.setUserId(userId);
        order.setTime(time);
        return order;
    }

}