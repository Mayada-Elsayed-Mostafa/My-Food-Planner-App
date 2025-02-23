package com.example.myfoodplannerapplication.home.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfoodplannerapplication.R;
import com.example.myfoodplannerapplication.database.MealLocalDataSource;
import com.example.myfoodplannerapplication.home.presenter.category.CategoriesImp;
import com.example.myfoodplannerapplication.home.presenter.meal.MealOfTheDayImp;
import com.example.myfoodplannerapplication.home.view.category.CategoriesView;
import com.example.myfoodplannerapplication.home.view.category.RVCategoriesAdapter;
import com.example.myfoodplannerapplication.home.view.meal.MealOfTheDayAdapter;
import com.example.myfoodplannerapplication.home.view.meal.MealOfTheDayView;
import com.example.myfoodplannerapplication.home.view.meal.OnMealOfTheDayClickListener;
import com.example.myfoodplannerapplication.model.Category;
import com.example.myfoodplannerapplication.model.InspirationMeal;
import com.example.myfoodplannerapplication.model.MealRepository;
import com.example.myfoodplannerapplication.network.MealRemoteDataSource;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements MealOfTheDayView, OnMealOfTheDayClickListener, CategoriesView {
    ImageView mealIMG;
    MealOfTheDayImp mealOfTheDayImp;
    MealOfTheDayAdapter mealOfTheDayAdapter;
    RVCategoriesAdapter rvCategoriesAdapter;
    RecyclerView recyclerView, rvCategories;
    CategoriesImp categoriesImp;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rv_meal);
        rvCategories = view.findViewById(R.id.rv_list_of_categories);
        mealIMG = view.findViewById(R.id.meal_iv);

        mealOfTheDayImp = new MealOfTheDayImp(MealRepository.getInstance(MealLocalDataSource.getInstance(getContext()), MealRemoteDataSource.getInstance()), this);
        mealOfTheDayImp.getMeal();

        categoriesImp = new CategoriesImp(MealRepository.getInstance(MealLocalDataSource.getInstance(getContext()), MealRemoteDataSource.getInstance()), this);
        categoriesImp.getCategories();

        mealOfTheDayAdapter = new MealOfTheDayAdapter(new ArrayList<>(), getContext(), this);
        recyclerView.setAdapter(mealOfTheDayAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        rvCategoriesAdapter = new RVCategoriesAdapter(getContext(), new ArrayList<>());
        rvCategories.setAdapter(rvCategoriesAdapter);
        rvCategories.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void setData(List<InspirationMeal> inspirationMeals) {
        if (inspirationMeals != null && !inspirationMeals.isEmpty()) {
            mealOfTheDayAdapter.setList(inspirationMeals);
        } else {
            Log.e("HomeFragment", "No meals available to display");
        }
    }

    @Override
    public void setCategory(List<Category> categoryList) {
        rvCategoriesAdapter.setList(categoryList);
    }

    @Override
    public void showErrMsg(String err) {

    }

    @Override
    public void onAddMealClicked(InspirationMeal meal) {
        HomeFragmentDirections.ActionHomeFragmentToMealDetailsFragment action =
                HomeFragmentDirections.actionHomeFragmentToMealDetailsFragment(meal);
        Navigation.findNavController(getView()).navigate(action);
    }

}
