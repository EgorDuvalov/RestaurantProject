package com.innowise.duvalov.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import javax.persistence.Entity;

@Entity
@Setter
@Getter
public class User {

    private int id;
    private String name;
    private Role role;
    private String password;

    public User() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(name, user.name) && Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, role);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", role=" + role +
                ", password='" + password + '\'' +
                '}';
    }
}
