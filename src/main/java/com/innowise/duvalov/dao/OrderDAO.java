package com.innowise.duvalov.dao;

import com.innowise.duvalov.entity.Order;

public interface OrderDAO {
    void setOrder(Order order);
    Order getOrder(int id);
}
