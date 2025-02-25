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
    String category;

    public FilterByImp(MealRepository _mealRepository, FilterByFragment _filterByFragment, String category) {
        this.mealRepository = _mealRepository;
        this.filterByFragment = _filterByFragment;
        this.category = category;
    }

    @SuppressLint("CheckResult")
    @Override
    public void getCategories() {
        mealRepository.getMealsByFiltersOverNetwork(category)
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
