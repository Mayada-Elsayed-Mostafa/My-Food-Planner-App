package com.example.myfoodplannerapplication.favoritemeals.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfoodplannerapplication.R;
import com.example.myfoodplannerapplication.database.MealLocalDataSource;
import com.example.myfoodplannerapplication.favoritemeals.presenter.FavoriteMealsImp;
import com.example.myfoodplannerapplication.model.InspirationMeal;
import com.example.myfoodplannerapplication.model.MealRepository;
import com.example.myfoodplannerapplication.network.MealRemoteDataSource;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment implements OnFavMealClickListener {

    FavoriteMealsImp favoriteMealsImp;
    RVFavMealsAdapter rvFavMealsAdapter;
    RecyclerView recyclerView;

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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        recyclerView = view.findViewById(R.id.rv_favMeals);
        rvFavMealsAdapter = new RVFavMealsAdapter(new ArrayList<>(), getContext(), this);
        recyclerView.setAdapter(rvFavMealsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        favoriteMealsImp = new FavoriteMealsImp(MealRepository.getInstance(MealLocalDataSource.getInstance(getContext()), MealRemoteDataSource.getInstance()), this);

        favoriteMealsImp.getFavMeals().observe(getViewLifecycleOwner(), new Observer<List<InspirationMeal>>() {
            @Override
            public void onChanged(List<InspirationMeal> _favMeals) {
                rvFavMealsAdapter.setList(_favMeals);
                rvFavMealsAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }

    @Override
    public void onFavMealClicked(InspirationMeal inspirationMeal) {

    }
}