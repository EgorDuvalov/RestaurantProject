package com.innowise.duvalov.dao;

import com.innowise.duvalov.entity.Order;

public interface AdminDAO extends UserDAO{
    void handleOrder(Order order);
}
