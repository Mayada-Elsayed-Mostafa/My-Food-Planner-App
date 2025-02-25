package com.example.myfoodplannerapplication.home.presenter.meal;

import android.annotation.SuppressLint;

import com.example.myfoodplannerapplication.home.view.meal.MealOfTheDayView;
import com.example.myfoodplannerapplication.model.MealRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealOfTheDayImp implements MealOfTheDay {

    private MealRepository mealRepository;
    private MealOfTheDayView mealOfTheDayView;

    public MealOfTheDayImp(MealRepository _mealRepository, MealOfTheDayView _mealOfTheDayView) {
        this.mealRepository = _mealRepository;
        this.mealOfTheDayView = _mealOfTheDayView;
    }

    @SuppressLint("CheckResult")
    @Override
    public void getMeal() {
        mealRepository.getMealFromNetwork()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        inspirationMealResponse -> mealOfTheDayView.setData(inspirationMealResponse.getMeals())
                );
    }
}
