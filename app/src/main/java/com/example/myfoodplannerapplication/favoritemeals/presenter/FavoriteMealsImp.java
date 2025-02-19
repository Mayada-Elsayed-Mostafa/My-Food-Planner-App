package com.example.myfoodplannerapplication.favoritemeals.presenter;

import androidx.lifecycle.LiveData;

import com.example.myfoodplannerapplication.favoritemeals.view.FavoriteFragment;
import com.example.myfoodplannerapplication.model.InspirationMeal;
import com.example.myfoodplannerapplication.model.MealRepository;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public class FavoriteMealsImp implements FavoriteMeals {

    MealRepository mealRepository;
    FavoriteFragment favoriteFragment;

    public FavoriteMealsImp(MealRepository _mealRepository, FavoriteFragment _favoriteFragment) {
        this.mealRepository = _mealRepository;
        this.favoriteFragment = _favoriteFragment;
    }

    public Flowable<List<InspirationMeal>> getFavMeals() {
        return mealRepository.getAllData();
    }

    @Override
    public void delete(InspirationMeal inspirationMeal) {
        mealRepository.delete(inspirationMeal);
    }
}
