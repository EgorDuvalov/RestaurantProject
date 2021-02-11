package com.innowise.duvalov.dao.impl;

import com.innowise.duvalov.entity.Meal;
import com.innowise.duvalov.pool.ConnectionPool;
import org.junit.Assert;
import org.junit.Test;

public class MealDAOImplTest {

    @Test
    public void testAddAndGetMeal() {
        ConnectionPool.INSTANCE.openPool();
        Meal expectedMeal = createMeal(1, "Apple Juice", 12.2);
        Meal returnedMeal = new MealDAOImpl().mealFromDB(1);
        Assert.assertEquals(expectedMeal, returnedMeal);
        ConnectionPool.INSTANCE.closePool();
    }

    public Meal createMeal(int id, String name, Double price) {
        Meal meal = new Meal();
        meal.setId(id);
        meal.setName(name);
        meal.setPrice(price);
        return meal;
    }

}