package com.example.myfoodplannerapplication.calender.presenter;

import com.example.myfoodplannerapplication.model.MealsOfWeek;
import com.example.myfoodplannerapplication.model.WeekMeals;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public interface Calendar {
    public Observable<List<WeekMeals>> getPlanMeals();

    //public Observable<List<MealsOfWeek>> getMealsForDate(String selectedDay);

    public void delete(WeekMeals meals);
}
