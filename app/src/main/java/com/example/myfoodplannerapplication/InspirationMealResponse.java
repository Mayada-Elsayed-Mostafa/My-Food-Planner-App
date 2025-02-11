package com.example.myfoodplannerapplication;

import java.util.List;

public class InspirationMealResponse {

    private List<InspirationMeal> meals;

    public InspirationMealResponse(List<InspirationMeal> meals) {
        this.meals = meals;
    }

    public List<InspirationMeal> getMeals() {
        return meals;
    }

}
