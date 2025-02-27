package com.example.myfoodplannerapplication.search.view;

import static android.view.View.GONE;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfoodplannerapplication.R;
import com.example.myfoodplannerapplication.database.MealLocalDataSource;
import com.example.myfoodplannerapplication.model.Category;
import com.example.myfoodplannerapplication.model.Country;
import com.example.myfoodplannerapplication.model.Ingredient;
import com.example.myfoodplannerapplication.model.MealRepository;
import com.example.myfoodplannerapplication.network.MealRemoteDataSource;
import com.example.myfoodplannerapplication.search.presenter.SearchImp;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class SearchFragment extends Fragment implements SearchView, OnSearchClickListener {

    EditText search;
    ImageView searchIMG;
    RecyclerView rvResults;
    RVCategoriesAdapter rvCategoriesAdapter;
    RVCountryAdapter rvCountryAdapter;
    RVIngredientAdapter rvIngredientAdapter;
    SearchImp searchImp;
    List<Category> categories;
    List<Country> countries;
    List<Ingredient> ingredients;
    Chip categoryChip, countryChip, ingredientChip;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvResults = view.findViewById(R.id.rv_search_results);
        rvResults.setLayoutManager(new LinearLayoutManager(getContext()));

        searchIMG = view.findViewById(R.id.iv_search);
        search = view.findViewById(R.id.search_bar);
        categoryChip = view.findViewById(R.id.chip_categories);
        countryChip = view.findViewById(R.id.chip_countries);
        ingredientChip = view.findViewById(R.id.chip_ingredients);

        searchImp = new SearchImp(MealRepository.getInstance(MealLocalDataSource.getInstance(getContext()), MealRemoteDataSource.getInstance()), this);
        searchImp.getCategories();
        searchImp.getCountries();
        searchImp.getIngredients();

        rvCategoriesAdapter = new RVCategoriesAdapter(getContext(), new ArrayList<>(), this);
        rvCountryAdapter = new RVCountryAdapter(getContext(), new ArrayList<>(), this);
        rvIngredientAdapter = new RVIngredientAdapter(getContext(), new ArrayList<>(), this);

        if (categoryChip.isChecked()) {
            countryChip.setChecked(false);
            ingredientChip.setChecked(false);
        } else if (countryChip.isChecked()) {
            categoryChip.setChecked(false);
            ingredientChip.setChecked(false);
        } else if (ingredientChip.isChecked()) {
            categoryChip.setChecked(false);
            countryChip.setChecked(false);
        }

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String query = charSequence.toString().toLowerCase();

                if (categoryChip.isChecked()) {
                    searchIMG.setVisibility(GONE);
                    rvResults.setAdapter(rvCategoriesAdapter);
                    List<Category> filteredList = categories.stream()
                            .filter(category -> category.getStrCategory().toLowerCase().contains(query))
                            .collect(Collectors.toList());
                    rvCategoriesAdapter.updateList(filteredList);
                }

                if (countryChip.isChecked()) {
                    searchIMG.setVisibility(GONE);
                    rvResults.setAdapter(rvCountryAdapter);
                    List<Country> filteredList = countries.stream()
                            .filter(country -> country.getStrArea().toLowerCase().contains(query))
                            .collect(Collectors.toList());
                    rvCountryAdapter.updateList(filteredList);
                }

                if (ingredientChip.isChecked()) {
                    searchIMG.setVisibility(GONE);
                    rvResults.setAdapter(rvIngredientAdapter);
                    List<Ingredient> filteredList = ingredients.stream()
                            .filter(ingredient -> ingredient.getStrIngredient().toLowerCase().contains(query))
                            .collect(Collectors.toList());
                    rvIngredientAdapter.updateList(filteredList);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        return view;
    }

    @Override
    public void setCategory(List<Category> categoryList) {

        categories = categoryList;
        if (categoryList != null && !categoryList.isEmpty()) {
            Log.d("SearchFragment", "categoryList Loaded: " + categoryList.size());
            rvCategoriesAdapter.setList(categoryList);
        } else {
            Log.e("SearchFragment", "No categories available to display");
        }
    }

    @Override
    public void setCountry(List<Country> countryList) {
        countries = countryList;
        if (countryList != null && !countryList.isEmpty()) {
            Log.d("SearchFragment", "countryList Loaded: " + countryList.size());
            rvCountryAdapter.setList(countryList);
        } else {
            Log.e("SearchFragment", "No categories available to display");
        }
    }

    @Override
    public void setIngredient(List<Ingredient> ingredientList) {

        ingredients = ingredientList;
        if (ingredientList != null && !ingredientList.isEmpty()) {
            Log.d("SearchFragment", "ingredientList Loaded: " + ingredientList.size());
            rvIngredientAdapter.setList(ingredientList);
        } else {
            Log.e("SearchFragment", "No ingredientList available to display");
        }
    }

    @Override
    public void showErrMsg(String err) {

    }

    @Override
    public void onAddSearchClicked(String filter) {
        SearchFragmentDirections.ActionSearchFragmentToFilterByFragment action =
                SearchFragmentDirections.actionSearchFragmentToFilterByFragment(filter);

        Navigation.findNavController(getView()).navigate(action);
    }
}