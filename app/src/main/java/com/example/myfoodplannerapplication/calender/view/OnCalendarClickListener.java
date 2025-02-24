package com.example.myfoodplannerapplication.calender.view;

import com.example.myfoodplannerapplication.model.InspirationMeal;
import com.example.myfoodplannerapplication.model.WeekMeals;

public interface OnCalendarClickListener {

    public void onMealClicked(WeekMeals meals);

    public void onImageMealClicked(InspirationMeal inspirationMeal);
}
