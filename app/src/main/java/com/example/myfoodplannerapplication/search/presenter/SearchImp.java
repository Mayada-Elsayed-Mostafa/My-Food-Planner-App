package com.example.myfoodplannerapplication.search.presenter;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.myfoodplannerapplication.model.MealRepository;
import com.example.myfoodplannerapplication.search.view.SearchFragment;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchImp implements Search {
    MealRepository mealRepository;
    SearchFragment searchFragment;


    public SearchImp(MealRepository _mealRepository, SearchFragment _searchFragment) {
        this.mealRepository = _mealRepository;
        this.searchFragment = _searchFragment;
    }

    @SuppressLint("CheckResult")
    @Override
    public void getCountries() {
        mealRepository.getCountryFromNetwork()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(countryResponse -> searchFragment.setCountry(countryResponse.getCountries()),
                        throwable -> Log.e("SearchFragment", "Error fetching countries: " + throwable.getMessage())
                );
    }

    @SuppressLint("CheckResult")
    @Override
    public void getCategories() {
        mealRepository.getCategoryFromNetwork()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(categoriesResponse -> searchFragment.setCategory(categoriesResponse.getCategories()),
                        throwable -> Log.e("SearchFragment", "Error fetching categories: " + throwable.getMessage())
                );
    }

    @SuppressLint("CheckResult")
    @Override
    public void getIngredients() {
        mealRepository.getIngredientFromNetwork()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ingredientResponse -> searchFragment.setIngredient(ingredientResponse.getIngredients()),
                        throwable -> Log.e("SearchFragment", "Error fetching ingredients: " + throwable.getMessage())
                );
    }

}
