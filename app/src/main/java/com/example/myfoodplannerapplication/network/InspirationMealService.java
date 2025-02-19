package com.example.myfoodplannerapplication.network;

import com.example.myfoodplannerapplication.model.InspirationMealResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface InspirationMealService {

    @GET("random.php")
    Call<InspirationMealResponse> getMeals();
}
