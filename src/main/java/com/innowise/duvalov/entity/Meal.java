package com.innowise.duvalov.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class Meal{
    private int id;
    private String name;
    private double price;

    public Meal() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meal meal = (Meal) o;
        return id == meal.id
                && Double.compare(meal.price, price) == 0
                && Objects.equals(name, meal.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price);
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
