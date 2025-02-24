package com.example.myfoodplannerapplication.home.presenter.Ingredients;

import android.util.Log;

import com.example.myfoodplannerapplication.home.view.HomeFragment;
import com.example.myfoodplannerapplication.model.Ingredient;
import com.example.myfoodplannerapplication.model.IngredientResponse;
import com.example.myfoodplannerapplication.model.MealRepository;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class IngredientsImp implements Ingredients {
    MealRepository mealRepository;
    HomeFragment homeFragment;


    public IngredientsImp(MealRepository _mealRepository, HomeFragment _homeFragment) {
        this.mealRepository = _mealRepository;
        this.homeFragment = _homeFragment;
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
                            homeFragment.setIngredient(ingredients);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("SearchFragment", "Error fetching ingredients: " + e.getMessage());
                    }
                });
    }
}
