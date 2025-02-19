package com.example.myfoodplannerapplication.details.view;

import com.example.myfoodplannerapplication.model.InspirationMeal;

import java.util.List;

public interface MealDetailsView {

    void setData(List<InspirationMeal> inspirationMealList);

    void showErrMsg(String err);
}
