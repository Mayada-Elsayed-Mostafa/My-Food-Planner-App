package com.example.myfoodplannerapplication.details.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.myfoodplannerapplication.R;
import com.example.myfoodplannerapplication.database.MealLocalDataSource;
import com.example.myfoodplannerapplication.details.presenter.MealDetailsImp;
import com.example.myfoodplannerapplication.model.InspirationMeal;
import com.example.myfoodplannerapplication.model.MealRepository;
import com.example.myfoodplannerapplication.network.MealRemoteDataSource;

import java.util.List;

public class MealDetailsFragment extends Fragment implements OnMealDetailsClickListener, MealDetailsView {

    TextView mealNameTV;
    TextView mealInstructionsTV;
    ImageView mealIMG;
    Button addToFav;
    MealDetailsImp mealDetailsImp;


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

        mealDetailsImp = new MealDetailsImp(
                MealRepository.getInstance(MealLocalDataSource.getInstance(getContext()), MealRemoteDataSource.getInstance()), this);

        addToFav = view.findViewById(R.id.btn_to_fav);

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

        addToFav.setOnClickListener(v -> onAddMealDetailsClicked(meal));

        return view;
    }


    @Override
    public void setData(List<InspirationMeal> inspirationMealList) {

    }

    @Override
    public void showErrMsg(String err) {

    }

    @Override
    public void onAddMealDetailsClicked(InspirationMeal inspirationMeal) {
        mealDetailsImp.addToFav(inspirationMeal);
    }
}