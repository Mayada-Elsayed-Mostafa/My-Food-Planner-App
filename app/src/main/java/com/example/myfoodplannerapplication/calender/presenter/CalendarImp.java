package com.example.myfoodplannerapplication.calender.presenter;

import com.example.myfoodplannerapplication.calender.view.CalenderFragment;
import com.example.myfoodplannerapplication.model.MealRepository;
import com.example.myfoodplannerapplication.model.WeekMeals;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class CalendarImp implements Calendar {
    MealRepository mealRepository;
    CalenderFragment calenderFragment;

    public CalendarImp(MealRepository mealRepository, CalenderFragment calenderFragment) {
        this.mealRepository = mealRepository;
        this.calenderFragment = calenderFragment;
    }

    @Override
    public Observable<List<WeekMeals>> getMealsForDate(String selectedDate) {
        return mealRepository.getMealsForDate(selectedDate);
    }

    @Override
    public void delete(WeekMeals meals) {
        mealRepository.deleteFromWeeklyPlan(meals);
    }

}
