package com.example.myfoodplannerapplication.home.presenter.category;

import android.util.Log;

import com.example.myfoodplannerapplication.home.view.category.CategoriesView;
import com.example.myfoodplannerapplication.model.Category;
import com.example.myfoodplannerapplication.model.CategoryRepository;
import com.example.myfoodplannerapplication.network.CategoryNetworkCallback;

import java.util.List;

public class CategoriesImp implements CategoryNetworkCallback, Categories {

    private CategoryRepository categoryRepository;
    private CategoriesView categoriesView;

    public CategoriesImp(CategoryRepository _categoryRepository, CategoriesView _categoriesView) {
        this.categoryRepository = _categoryRepository;
        this.categoriesView = _categoriesView;
    }

    @Override
    public void onSuccess(List<Category> categoryList) {
        categoriesView.setCategory(categoryList);
    }

    @Override
    public void onFailure(String errorMessage) {

    }

    @Override
    public void getCategory() {
        categoryRepository.getAllData(this);

    }
}
