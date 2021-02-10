package com.innowise.duvalov.dao;

import com.innowise.duvalov.entity.Order;

public interface OrderDAO {
    void orderToDB(Order order);
    Order orderFromDB(int id);
}
