package com.example.myfoodplannerapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    public static final String SINGLE_RANDOM_MEAL_URL = "https://www.themealdb.com/api/json/v1/1/";
    List<InspirationMeal> meals;
    TextView nameOfMeal;
    ImageView imageOfMeal;
    String imageURL;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        nameOfMeal = view.findViewById(R.id.tv_name_meal);
        imageOfMeal = view.findViewById(R.id.iv_meal_img);

        loadRandomMeal();

        return view;
    }

    private void loadRandomMeal() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SINGLE_RANDOM_MEAL_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        InspirationMealService mealService = retrofit.create(InspirationMealService.class);
        Call<InspirationMealResponse> call = mealService.getMeals();

        call.enqueue(new Callback<InspirationMealResponse>() {
            @Override
            public void onResponse(Call<InspirationMealResponse> call, Response<InspirationMealResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    meals = response.body().getMeals();
                    if (meals != null && !meals.isEmpty()) {
                        nameOfMeal.setText(meals.get(0).getStrMeal());
                        imageURL = meals.get(0).getStrMealThumb();
                        Glide.with(requireContext())
                                .load(imageURL)
                                .apply(new RequestOptions().override(200, 200))
                                .into(imageOfMeal);
                    }
                }
            }

            @Override
            public void onFailure(Call<InspirationMealResponse> call, Throwable t) {
                // يمكنك إضافة Toast أو Log لمعرفة سبب الخطأ
            }
        });
    }
}
