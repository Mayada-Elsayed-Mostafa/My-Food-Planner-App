package com.example.myfoodplannerapplication.model;

import java.util.List;

public class CategoryResponse {

    private List<Category> categoryList;

    public CategoryResponse(List<Category> categories) {
        this.categoryList = categories;
    }

    public List<Category> getCategories() {
        return categoryList;
    }
}
