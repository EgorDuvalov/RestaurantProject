package com.innowise.duvalov.dao;

import com.innowise.duvalov.entity.User;

public interface UserDAO {
    void userToDB(User user);
    User userFromDB(int id);
}
