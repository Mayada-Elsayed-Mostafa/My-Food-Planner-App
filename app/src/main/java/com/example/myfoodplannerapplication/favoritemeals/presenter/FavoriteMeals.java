package com.example.myfoodplannerapplication.favoritemeals.presenter;

import com.example.myfoodplannerapplication.model.InspirationMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public interface FavoriteMeals {

    public Observable<List<InspirationMeal>> getFavMeals();

    public void delete(InspirationMeal inspirationMeal);

}
