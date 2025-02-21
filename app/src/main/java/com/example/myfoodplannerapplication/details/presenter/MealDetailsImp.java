package com.example.myfoodplannerapplication.details.presenter;

import com.example.myfoodplannerapplication.details.view.MealDetailsView;
import com.example.myfoodplannerapplication.model.InspirationMeal;
import com.example.myfoodplannerapplication.model.MealRepository;

public class MealDetailsImp implements MealDetails {

    MealRepository mealRepository;
    MealDetailsView mealDetailsView;

    public MealDetailsImp(MealRepository _mealRepository, MealDetailsView _mealDetailsView) {
        this.mealRepository = _mealRepository;
        this.mealDetailsView = _mealDetailsView;
    }

    @Override
    public void addToFav(InspirationMeal inspirationMeal) {
        mealRepository.insert(inspirationMeal);
    }
}
