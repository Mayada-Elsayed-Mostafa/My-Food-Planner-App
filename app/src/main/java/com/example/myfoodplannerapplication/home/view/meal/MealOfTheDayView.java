package com.example.myfoodplannerapplication.home.view.meal;

import com.example.myfoodplannerapplication.model.InspirationMeal;

import java.util.List;

public interface MealOfTheDayView {
    void setData(List<InspirationMeal> inspirationMeals);

    void showErrMsg(String err);
}
