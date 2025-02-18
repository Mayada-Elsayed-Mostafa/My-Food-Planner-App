package com.example.myfoodplannerapplication.mealoftheday.presenter;

import android.util.Log;

import com.example.myfoodplannerapplication.mealoftheday.view.MealOfTheDayView;
import com.example.myfoodplannerapplication.model.InspirationMeal;
import com.example.myfoodplannerapplication.model.MealRepository;
import com.example.myfoodplannerapplication.network.NetworkCallback;

import java.util.List;

public class MealOfTheDayImp implements NetworkCallback, MealOfTheDay {

    private MealRepository mealRepository;
    private MealOfTheDayView mealOfTheDayView;

    public MealOfTheDayImp(MealRepository _mealRepository, MealOfTheDayView _mealOfTheDayView) {
        this.mealRepository = _mealRepository;
        this.mealOfTheDayView = _mealOfTheDayView;
    }


    @Override
    public void getMeal() {
        mealRepository.getAllData(this);
    }

    @Override
    public void addToFav(InspirationMeal inspirationMeal) {
        mealRepository.insert(inspirationMeal);
    }

    @Override
    public void onSuccess(List<InspirationMeal> inspirationMeals) {
        Log.d("TAG", "onSuccess: " + inspirationMeals.size());
        mealOfTheDayView.setData(inspirationMeals);
    }

    @Override
    public void onFailure(String errorMessage) {

    }
}
