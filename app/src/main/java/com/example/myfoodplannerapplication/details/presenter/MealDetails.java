package com.example.myfoodplannerapplication.details.presenter;

import com.example.myfoodplannerapplication.model.InspirationMeal;
import com.example.myfoodplannerapplication.model.MealsOfWeek;

public interface MealDetails {
    public void addToFav(InspirationMeal inspirationMeal);

    public void addToCalendar(MealsOfWeek mealsOfWeek);
}
