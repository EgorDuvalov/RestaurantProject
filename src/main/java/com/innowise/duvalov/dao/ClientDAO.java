package com.innowise.duvalov.dao;

import com.innowise.duvalov.entity.Order;

public interface ClientDAO extends UserDAO{

    void payTheBill();

    void addToOrder(Order order, String name);
}
