package com.example.myfoodplannerapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

public class MealDetailsFragment extends Fragment {

    TextView mealNameTV;
    TextView mealInstructionsTV;
    ImageView mealIMG;

    public MealDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meal_details, container, false);

        InspirationMeal meal = MealDetailsFragmentArgs.fromBundle(getArguments()).getMeal();


        String mealName = meal.getStrMeal();
        String mealInstructions = meal.getStrInstructions();
        String mealImage = meal.getStrMealThumb();

        mealNameTV = view.findViewById(R.id.tv_meal_name_in_details);
        mealInstructionsTV = view.findViewById(R.id.tv_meal_instructions_in_details);
        mealIMG = view.findViewById(R.id.img_meal_in_details);

        mealNameTV.setText(mealName);

        mealInstructionsTV.setText(mealInstructions);

        Glide.with(requireContext())
                .load(mealImage)
                .into(mealIMG);

        return view;
    }
}