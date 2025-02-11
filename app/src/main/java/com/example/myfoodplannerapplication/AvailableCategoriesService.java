package com.example.myfoodplannerapplication;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AvailableCategoriesService {

    @GET("categories.php")
    Call<AvailableCategoriesResponse> getAvailableCategories();
}
