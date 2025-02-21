package com.example.myfoodplannerapplication.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IngredientResponse {

    @SerializedName("meals")
    private List<Ingredient> ingredients;

    public IngredientResponse(List<Ingredient> _ingredients) {
        this.ingredients = _ingredients;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }
}
