package com.example.myfoodplannerapplication.search.presenter;

import android.util.Log;

import com.example.myfoodplannerapplication.model.CategoriesResponse;
import com.example.myfoodplannerapplication.model.Category;
import com.example.myfoodplannerapplication.model.Country;
import com.example.myfoodplannerapplication.model.CountryResponse;
import com.example.myfoodplannerapplication.model.Ingredient;
import com.example.myfoodplannerapplication.model.IngredientResponse;
import com.example.myfoodplannerapplication.model.MealRepository;
import com.example.myfoodplannerapplication.search.view.SearchFragment;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchImp implements Search {
    MealRepository mealRepository;
    SearchFragment searchFragment;


    public SearchImp(MealRepository _mealRepository, SearchFragment _searchFragment) {
        this.mealRepository = _mealRepository;
        this.searchFragment = _searchFragment;
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
                        if (countries == null || countries.isEmpty()) {
                            Log.e("SearchFragment", "No countries available to display");
                        } else {
                            searchFragment.setCountry(countries);
                        }
                    }


                    @Override
                    public void onError(Throwable e) {
                        Log.e("SearchFragment", "Error fetching countries: " + e.getMessage());
                    }
                });
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
                        if (categories == null || categories.isEmpty()) {
                            Log.e("SearchFragment", "No categories available to display");
                        } else {
                            searchFragment.setCategory(categories);
                        }
                    }


                    @Override
                    public void onError(Throwable e) {
                        Log.e("SearchFragment", "Error fetching categories: " + e.getMessage());
                    }
                });
    }

    @Override
    public void getIngredients() {

        mealRepository.getIngredientFromNetwork()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<IngredientResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onSuccess(IngredientResponse ingredientsResponse) {
                        List<Ingredient> ingredients = ingredientsResponse.getIngredients();
                        if (ingredients == null || ingredients.isEmpty()) {
                            Log.e("SearchFragment", "No ingredients available to display");
                        } else {
                            searchFragment.setIngredient(ingredients);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("SearchFragment", "Error fetching ingredients: " + e.getMessage());
                    }
                });
    }

}
