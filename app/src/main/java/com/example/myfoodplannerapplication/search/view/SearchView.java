package com.example.myfoodplannerapplication.search.view;

import com.example.myfoodplannerapplication.model.Category;
import com.example.myfoodplannerapplication.model.Country;
import com.example.myfoodplannerapplication.model.Ingredient;

import java.util.List;

public interface SearchView {

    void setCategory(List<Category> categoryList);

    void setCountry(List<Country> countryList);

    void setIngredient(List<Ingredient> ingredientList);

    void showErrMsg(String err);
}
