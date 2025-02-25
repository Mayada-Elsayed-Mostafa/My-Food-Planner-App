package com.example.myfoodplannerapplication.details.presenter;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.myfoodplannerapplication.details.view.MealDetailsFragment;
import com.example.myfoodplannerapplication.model.InspirationMeal;
import com.example.myfoodplannerapplication.model.MealRepository;
import com.example.myfoodplannerapplication.model.WeekMeals;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

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

    @SuppressLint("CheckResult")
    @Override
    public void getMealById(String id) {
        mealRepository.getMealsByFilterOfIdOverNetwork(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meal -> {
                    mealDetailsFragment.displayMeal(meal.getMeals().get(0));
                });
    }


}
