package com.example.myfoodplannerapplication.network;

import com.example.myfoodplannerapplication.model.ByFilterResponse;
import com.example.myfoodplannerapplication.model.CategoriesResponse;
import com.example.myfoodplannerapplication.model.CountryResponse;
import com.example.myfoodplannerapplication.model.IngredientResponse;
import com.example.myfoodplannerapplication.model.InspirationMealResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface InspirationMealService {

    @GET("/api/json/v1/1/random.php")
    Single<InspirationMealResponse> getMeals();

    @GET("/api/json/v1/1/categories.php")
    Single<CategoriesResponse> getCategories();

    @GET("/api/json/v1/1/list.php?a=list")
    Single<CountryResponse> getCountries();

    @GET("/api/json/v1/1/list.php?i=list")
    Single<IngredientResponse> getIngredients();

    @GET("/api/json/v1/1/filter.php")
    Single<ByFilterResponse> getMealsByCategory(@Query("c") String category);

    @GET("/api/json/v1/1/filter.php")
    Single<ByFilterResponse> getMealsByCountry(@Query("a") String area);

    @GET("/api/json/v1/1/filter.php")
    Single<ByFilterResponse> getMealsByIngredient(@Query("i") String ingredient);
}
