package com.innowise.duvalov.entity;

import java.util.Random;

public class Admin extends User{
    private String password;

    public Admin(){
        super();
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
