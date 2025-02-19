package com.example.myfoodplannerapplication.favoritemeals.presenter;

import com.example.myfoodplannerapplication.model.InspirationMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public interface FavoriteMeals {

    public Flowable<List<InspirationMeal>> getFavMeals();

    public void delete(InspirationMeal inspirationMeal);

}
