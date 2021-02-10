package com.innowise.duvalov.entity;

import java.io.Serializable;
import java.sql.Time;
import java.util.Objects;
//import javax.persistence.Entity;

//@Entity
public class Order implements Serializable {
    private int id;
    private boolean status;
    private int userId;
    private double bill;
    private Time time;

    public Order() {
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", status=" + status +
                ", user_id=" + userId +
                ", bill=" + bill +
                ", time=" + time +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id && status == order.status
                && userId == order.userId
                && Double.compare(order.bill, bill) == 0
                && Objects.equals(time, order.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, userId, bill, time);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getBill() {
        return bill;
    }

    public void setBill(double bill) {
        this.bill = bill;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}
