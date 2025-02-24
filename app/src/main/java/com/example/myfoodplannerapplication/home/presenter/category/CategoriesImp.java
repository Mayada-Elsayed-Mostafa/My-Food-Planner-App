package com.example.myfoodplannerapplication.home.presenter.category;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.myfoodplannerapplication.home.view.HomeFragment;
import com.example.myfoodplannerapplication.model.MealRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CategoriesImp implements Categories {

    private HomeFragment categoriesHomeFragment;
    private MealRepository mealRepository;

    public CategoriesImp(MealRepository _mealRepository, HomeFragment _categoriesHomeFragment) {
        this.mealRepository = _mealRepository;
        this.categoriesHomeFragment = _categoriesHomeFragment;
    }

    @SuppressLint("CheckResult")
    @Override
    public void getCategories() {
        mealRepository.getCategoryFromNetwork()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        categoriesResponse -> categoriesHomeFragment.setCategory(categoriesResponse.getCategories()),
                        throwable -> Log.e("HomeFragment", "Error fetching categories: " + throwable.getMessage())
                );
    }
}
