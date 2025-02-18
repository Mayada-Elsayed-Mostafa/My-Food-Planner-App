package com.example.myfoodplannerapplication.mealoftheday.view;

import com.example.myfoodplannerapplication.model.InspirationMeal;

import java.util.List;

public interface MealOfTheDayView {
    void setData(List<InspirationMeal> inspirationMeals);

    void showErrMsg(String err);
}
