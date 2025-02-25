package com.example.myfoodplannerapplication.details.presenter;

import com.example.myfoodplannerapplication.details.view.MealDetailsFragment;
import com.example.myfoodplannerapplication.model.InspirationMeal;
import com.example.myfoodplannerapplication.model.MealRepository;
import com.example.myfoodplannerapplication.model.WeekMeals;

public class MealDetailsImp implements MealDetails {

    MealRepository mealRepository;
    MealDetailsFragment mealDetailsFragment;

    public MealDetailsImp(MealRepository _mealRepository, MealDetailsFragment _mealDetailsFragment) {
        this.mealRepository = _mealRepository;
        this.mealDetailsFragment = _mealDetailsFragment;
    }

    @Override
    public void addToFav(InspirationMeal inspirationMeal) {
        mealRepository.insert(inspirationMeal);
    }

    @Override
    public void addToCalendar(WeekMeals meals) {
        mealRepository.insertToWeeklyPlan(meals);

    }

}
