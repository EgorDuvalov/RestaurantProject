package com.innowise.duvalov.entity;

import lombok.Getter;

@Getter
public enum Role {
    Client(0),
    Admin(1);

    Role(int roleNumber) {
        this.roleNumber = roleNumber;
    }
    private final int roleNumber;
}
