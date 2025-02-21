package com.example.myfoodplannerapplication.model;

import java.util.List;

public class CategoriesResponse {
    private List<Category> categories;

    public CategoriesResponse(List<Category> _categories) {
        this.categories = _categories;
    }

    public List<Category> getCategories() {
        return categories;
    }

}
