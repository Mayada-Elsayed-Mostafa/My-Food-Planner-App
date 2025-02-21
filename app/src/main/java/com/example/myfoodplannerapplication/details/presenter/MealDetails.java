package com.example.myfoodplannerapplication.details.presenter;

import com.example.myfoodplannerapplication.model.InspirationMeal;

public interface MealDetails {
    public void addToFav(InspirationMeal inspirationMeal);

    public void addToCalendar(InspirationMeal inspirationMeal);
}
