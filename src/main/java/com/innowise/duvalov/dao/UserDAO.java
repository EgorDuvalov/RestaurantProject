package com.innowise.duvalov.dao;

import com.innowise.duvalov.entity.User;

public interface UserDAO {
    void setUser(User user);
    User getUser(int id);
}
