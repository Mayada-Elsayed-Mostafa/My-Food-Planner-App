package com.example.myfoodplannerapplication.filtered.mealsFilteredBy.presenter;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.myfoodplannerapplication.filtered.mealsFilteredBy.view.FilterByFragment;
import com.example.myfoodplannerapplication.model.MealRepository;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FilterByImp implements FilterBy {

    MealRepository mealRepository;
    FilterByFragment filterByFragment;
    String filter;

    public FilterByImp(MealRepository _mealRepository, FilterByFragment _filterByFragment, String _filter) {
        this.mealRepository = _mealRepository;
        this.filterByFragment = _filterByFragment;
        this.filter = _filter;
    }

    @SuppressLint("CheckResult")
    @Override
    public void getMealsByCategory() {
        mealRepository.getMealsByFilterOfCategoryOverNetwork(filter)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response != null && response.getByFilters() != null) {
                        filterByFragment.setFilter(response.getByFilters());
                    } else {
                        filterByFragment.setFilter(new ArrayList<>());
                    }
                }, throwable -> {
                    Log.e("FilterByImp", "Error fetching filtered meals: " + throwable.getMessage());
                    filterByFragment.setFilter(new ArrayList<>());
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void getMealsByCountry() {
        mealRepository.getMealsByFilterOfCountryOverNetwork(filter)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response != null && response.getByFilters() != null) {
                        filterByFragment.setFilter(response.getByFilters());
                    } else {
                        filterByFragment.setFilter(new ArrayList<>());
                    }
                }, throwable -> {
                    Log.e("FilterByImp", "Error fetching filtered meals: " + throwable.getMessage());
                    filterByFragment.setFilter(new ArrayList<>());
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void getMealsByIngredient() {
        mealRepository.getMealsByFilterOfIngredientsOverNetwork(filter)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response != null && response.getByFilters() != null) {
                        filterByFragment.setFilter(response.getByFilters());
                    } else {
                        filterByFragment.setFilter(new ArrayList<>());
                    }
                }, throwable -> {
                    Log.e("FilterByImp", "Error fetching filtered meals: " + throwable.getMessage());
                    filterByFragment.setFilter(new ArrayList<>());
                });
    }
}
