package com.example.myfoodplannerapplication.network;

import com.example.myfoodplannerapplication.model.InspirationMeal;

import java.util.List;

public interface NetworkCallback {

    void onSuccess(List<InspirationMeal> inspirationMeals);

    void onFailure(String errorMessage);
}
