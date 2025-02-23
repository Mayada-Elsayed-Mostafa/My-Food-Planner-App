package com.example.myfoodplannerapplication.calender.presenter;

import com.example.myfoodplannerapplication.model.MealsOfWeek;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public interface Calendar {
    public Observable<List<MealsOfWeek>> getPlanMeals();

    //public Observable<List<MealsOfWeek>> getMealsForDate(String selectedDay);

    public void delete(MealsOfWeek mealsOfWeek);
}
