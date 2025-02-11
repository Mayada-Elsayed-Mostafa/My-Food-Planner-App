package com.example.myfoodplannerapplication;

import java.util.List;

public class AvailableCategoriesResponse {

    private final List<AvailableCategories> categories;

    public AvailableCategoriesResponse(List<AvailableCategories> categories) {
        this.categories = categories;
    }

    public List<AvailableCategories> getCategories() {
        return categories;
    }
}
