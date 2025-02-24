package com.example.myfoodplannerapplication.details.view;

import com.example.myfoodplannerapplication.model.InspirationMeal;
import com.example.myfoodplannerapplication.model.WeekMeals;

public interface OnMealDetailsClickListener {

    public void onAddFavMealDetailsClicked(InspirationMeal inspirationMeal);

    public void onAddCalendarMealDetailsClicked(WeekMeals meals);
}

