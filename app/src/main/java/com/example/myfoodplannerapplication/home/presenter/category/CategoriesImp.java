package com.example.myfoodplannerapplication.home.presenter.category;

import com.example.myfoodplannerapplication.home.view.category.CategoriesView;
import com.example.myfoodplannerapplication.model.MealRepository;

public class CategoriesImp implements Categories {

    private CategoriesView categoriesView;
    private MealRepository mealRepository;

    public CategoriesImp(MealRepository _mealRepository, CategoriesView _categoriesView) {
        this.mealRepository = _mealRepository;
        this.categoriesView = _categoriesView;
    }


    @Override
    public void getCategory() {

    }
}
