package com.example.myfoodplannerapplication.home.presenter.country;

import android.util.Log;

import com.example.myfoodplannerapplication.home.view.HomeFragment;
import com.example.myfoodplannerapplication.model.Country;
import com.example.myfoodplannerapplication.model.CountryResponse;
import com.example.myfoodplannerapplication.model.MealRepository;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CountriesImp implements Countries {
    private HomeFragment countriesHomeFragment;
    private MealRepository mealRepository;

    public CountriesImp(MealRepository _mealRepository, HomeFragment _countriesHomeFragment) {
        this.mealRepository = _mealRepository;
        this.countriesHomeFragment = _countriesHomeFragment;
    }


    @Override
    public void getCountries() {
        mealRepository.getCountryFromNetwork()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<CountryResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onSuccess(CountryResponse countryResponse) {
                        List<Country> countries = countryResponse.getCountries();

                        countriesHomeFragment.setCountry(countries);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("HomeFragment", "Error fetching categories: " + e.getMessage());
                    }
                });
    }
}
