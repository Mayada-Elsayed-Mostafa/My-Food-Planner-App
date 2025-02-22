package com.example.myfoodplannerapplication.favoritemeals.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfoodplannerapplication.R;
import com.example.myfoodplannerapplication.database.MealLocalDataSource;
import com.example.myfoodplannerapplication.favoritemeals.presenter.FavoriteMealsImp;
import com.example.myfoodplannerapplication.model.InspirationMeal;
import com.example.myfoodplannerapplication.model.MealRepository;
import com.example.myfoodplannerapplication.network.MealRemoteDataSource;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavoriteFragment extends Fragment implements OnFavMealClickListener {

    FavoriteMealsImp favoriteMealsImp;
    RVFavMealsAdapter rvFavMealsAdapter;
    RecyclerView recyclerView;
    OnFavMealClickListener onFavMealClickListener;

    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        recyclerView = view.findViewById(R.id.rv_favMeals);
        rvFavMealsAdapter = new RVFavMealsAdapter(new ArrayList<>(), getContext(), this);
        recyclerView.setAdapter(rvFavMealsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        favoriteMealsImp = new FavoriteMealsImp(MealRepository.getInstance(MealLocalDataSource.getInstance(getContext()), MealRemoteDataSource.getInstance()), this);

        favoriteMealsImp.getFavMeals().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                mealList -> {
                    rvFavMealsAdapter.setList(mealList);
                    rvFavMealsAdapter.notifyDataSetChanged();
                },
                throwable -> {
                    Log.d("TAG", "onFavMealClicked: ");
                });


        return view;
    }

    @Override
    public void onFavMealClicked(InspirationMeal inspirationMeal) {

        favoriteMealsImp.delete(inspirationMeal);
        Toast.makeText(getContext(), "Meal Deleted", Toast.LENGTH_SHORT).show();

        favoriteMealsImp.getFavMeals().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                updatedMealList -> {
                    rvFavMealsAdapter.setList(updatedMealList);
                    rvFavMealsAdapter.notifyDataSetChanged();
                }
                , throwable -> {
                    Log.d("TAG", "onFavMealClicked: ");
                });


    }
}