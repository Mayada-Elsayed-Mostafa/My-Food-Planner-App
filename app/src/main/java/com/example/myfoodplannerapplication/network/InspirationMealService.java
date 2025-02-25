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

    @GET("random.php")
    Single<InspirationMealResponse> getMeals();

    @GET("categories.php")
    Single<CategoriesResponse> getCategories();

    @GET("list.php?a=list")
    Single<CountryResponse> getCountries();

    @GET("list.php?i=list")
    Single<IngredientResponse> getIngredients();

    @GET("filter.php")
    Single<ByFilterResponse> getMealsByCategory(@Query("c") String category);

    @GET("filter.php")
    Single<ByFilterResponse> getMealsByCountry(@Query("a") String area);

    @GET("filter.php")
    Single<ByFilterResponse> getMealsByIngredient(@Query("i") String ingredient);

    @GET("lookup.php")
    Single<InspirationMealResponse> getMealsById(@Query("i") String id);
}
