package com.example.myfoodplannerapplication.calender.presenter;

import com.example.myfoodplannerapplication.model.MealsOfWeek;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public interface Calendar {
    public Observable<List<MealsOfWeek>> getPlanMeals();

    public void delete(MealsOfWeek mealsOfWeek);
}
