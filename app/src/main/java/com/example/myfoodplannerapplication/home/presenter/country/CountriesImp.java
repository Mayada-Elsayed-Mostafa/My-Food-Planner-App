package com.example.myfoodplannerapplication.home.presenter.country;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.myfoodplannerapplication.home.view.HomeFragment;
import com.example.myfoodplannerapplication.model.MealRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CountriesImp implements Countries {
    private HomeFragment countriesHomeFragment;
    private MealRepository mealRepository;

    public CountriesImp(MealRepository _mealRepository, HomeFragment _countriesHomeFragment) {
        this.mealRepository = _mealRepository;
        this.countriesHomeFragment = _countriesHomeFragment;
    }


    @SuppressLint("CheckResult")
    @Override
    public void getCountries() {
        mealRepository.getCountryFromNetwork()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(countryResponse -> countriesHomeFragment.setCountry(countryResponse.getCountries()),
                        throwable -> Log.e("HomeFragment", "Error fetching categories: " + throwable.getMessage())
                );
    }
}