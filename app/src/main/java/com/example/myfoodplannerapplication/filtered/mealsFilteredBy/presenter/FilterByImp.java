package com.example.myfoodplannerapplication.filtered.mealsFilteredBy.presenter;

import com.example.myfoodplannerapplication.filtered.mealsFilteredBy.view.FilterByFragment;
import com.example.myfoodplannerapplication.model.MealRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FilterByImp implements FilterBy {

    MealRepository mealRepository;
    FilterByFragment filterByFragment;
    String category = "";


    public FilterByImp(MealRepository _mealRepository, FilterByFragment _filterByFragment) {
        this.mealRepository = _mealRepository;
        this.filterByFragment = _filterByFragment;
    }

    @Override
    public void getCategories() {
        mealRepository.getMealsByFiltersOverNetwork(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe();

    }
}
