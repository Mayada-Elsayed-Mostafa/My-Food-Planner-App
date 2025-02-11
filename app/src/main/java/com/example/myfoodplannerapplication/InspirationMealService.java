package com.example.myfoodplannerapplication;

import retrofit2.Call;
import retrofit2.http.GET;

public interface InspirationMealService {

    @GET("random.php")
    Call<InspirationMealResponse> getMeals();

}
