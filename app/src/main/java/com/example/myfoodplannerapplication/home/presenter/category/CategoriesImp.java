package com.example.myfoodplannerapplication.home.presenter.category;

import android.util.Log;

import com.example.myfoodplannerapplication.home.view.HomeFragment;
import com.example.myfoodplannerapplication.model.CategoriesResponse;
import com.example.myfoodplannerapplication.model.Category;
import com.example.myfoodplannerapplication.model.MealRepository;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CategoriesImp implements Categories {

    private HomeFragment categoriesHomeFragment;
    private MealRepository mealRepository;

    public CategoriesImp(MealRepository _mealRepository, HomeFragment _categoriesHomeFragment) {
        this.mealRepository = _mealRepository;
        this.categoriesHomeFragment = _categoriesHomeFragment;
    }


    @Override
    public void getCategories() {
        mealRepository.getCategoryFromNetwork()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<CategoriesResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onSuccess(CategoriesResponse categoriesResponse) {
                        List<Category> categories = categoriesResponse.getCategories();

                        categoriesHomeFragment.setCategory(categories);
                    }


                    @Override
                    public void onError(Throwable e) {
                        Log.e("HomeFragment", "Error fetching categories: " + e.getMessage());
                    }
                });
    }
}
