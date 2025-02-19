package com.example.myfoodplannerapplication.home.view.category;

import com.example.myfoodplannerapplication.model.Category;

import java.util.List;

public interface CategoriesView {
    void setCategory(List<Category> categoryList);

    void showErrMsg(String err);
}
