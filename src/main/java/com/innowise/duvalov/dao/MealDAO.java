package com.innowise.duvalov.dao;

import com.innowise.duvalov.entity.Meal;

public interface MealDAO {
    void mealToDB(Meal meal);
    Meal mealFromDB(int id);

}
