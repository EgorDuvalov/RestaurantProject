package com.innowise.duvalov.dao;

import com.innowise.duvalov.entity.Meal;

public interface MealDAO {
    void setMeal(Meal meal);
    Meal getMeal(int id);

}
