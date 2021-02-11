package com.innowise.duvalov.dao;

import com.innowise.duvalov.entity.Order;
import com.innowise.duvalov.entity.User;

public interface UserDAO {
    void userToDB(User user);

    User userFromDB(int id);

    //Admin
    void handleOrder(Order order);

    //Client
    void payTheBill();

    void addToOrder(Order order, String name);
}
