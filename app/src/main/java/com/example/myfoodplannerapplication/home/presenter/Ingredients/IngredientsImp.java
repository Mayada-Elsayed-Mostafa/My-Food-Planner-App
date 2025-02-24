package com.example.myfoodplannerapplication.home.presenter.Ingredients;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.myfoodplannerapplication.home.view.HomeFragment;
import com.example.myfoodplannerapplication.model.MealRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class IngredientsImp implements Ingredients {
    MealRepository mealRepository;
    HomeFragment homeFragment;


    public IngredientsImp(MealRepository _mealRepository, HomeFragment _homeFragment) {
        this.mealRepository = _mealRepository;
        this.homeFragment = _homeFragment;
    }

    @SuppressLint("CheckResult")
    @Override
    public void getIngredients() {
        mealRepository.getIngredientFromNetwork()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ingredientResponse -> homeFragment.setIngredient(ingredientResponse.getIngredients()),
                        throwable -> Log.e("SearchFragment", "Error fetching ingredients: " + throwable.getMessage())
                );
    }
}