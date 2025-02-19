package com.example.myfoodplannerapplication.network;

import android.util.Log;

import com.example.myfoodplannerapplication.model.Category;
import com.example.myfoodplannerapplication.model.CategoryResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CategoryRemoteDataSource {

    public static String SINGLE_RANDOM_MEAL_URL = "https://www.themealdb.com/api/json/v1/1/";
    private CategoryService categoryService;
    private static CategoryRemoteDataSource categoryRemoteDataSource = null;

    public CategoryRemoteDataSource() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SINGLE_RANDOM_MEAL_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        categoryService = retrofit.create(CategoryService.class);
    }

    public static CategoryRemoteDataSource getInstance() {
        if (categoryRemoteDataSource == null) {
            categoryRemoteDataSource = new CategoryRemoteDataSource();
        }

        return categoryRemoteDataSource;
    }

    public void getCategoryOverNetwork(CategoryNetworkCallback callback) {
        Call<CategoryResponse> call = categoryService.getCategories();

        call.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Category> categories = response.body().getCategories();
                    if (categories != null && !categories.isEmpty()) {
                        Log.i("TAG", "onResponse: " + categories);
                        callback.onSuccess(categories);
                    } else {
                        Log.e("TAG", "Categories list is empty.");
                        callback.onFailure("No categories available.");
                    }
                } else {
                    Log.e("TAG", "Response is not successful or body is null.");
                    callback.onFailure("Failed to retrieve categories.");
                }
            }


            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                Log.e("TAG", "Network request failed: " + t.getMessage());
                callback.onFailure(t.getMessage());
            }
        });
    }
}
