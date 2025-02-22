package com.example.myfoodplannerapplication.details.view;

import com.example.myfoodplannerapplication.model.InspirationMeal;
import com.example.myfoodplannerapplication.model.MealsOfWeek;

public interface OnMealDetailsClickListener {

    public void onAddFavMealDetailsClicked(InspirationMeal inspirationMeal);
    public void onAddCalendarMealDetailsClicked(MealsOfWeek mealsOfWeek);
}

