package com.example.myfoodplannerapplication.details.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    TextView mealInstructionsTV, mealAreaTV;
    ImageView mealIMG;
    ImageView addToFav, addToCalendar;
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

        addToFav = view.findViewById(R.id.add_to_fav);
        addToCalendar = view.findViewById(R.id.add_to_calendar);

        InspirationMeal meal = MealDetailsFragmentArgs.fromBundle(getArguments()).getMeal();


        String mealName = meal.getStrMeal();
        String mealInstructions = meal.getStrInstructions();
        String mealImage = meal.getStrMealThumb();
        String mealCountry = meal.getStrArea();

        mealNameTV = view.findViewById(R.id.tv_meal_name_in_details);
        mealInstructionsTV = view.findViewById(R.id.tv_meal_instructions_in_details);
        mealIMG = view.findViewById(R.id.img_meal_in_details);
        mealAreaTV = view.findViewById(R.id.tv_area);

        mealNameTV.setText(mealName);
        mealAreaTV.setText(mealCountry);

        String[] instructionsList = mealInstructions.split(".\n");

        StringBuilder formattedInstructions = new StringBuilder();
        for (String instruction : instructionsList) {
            formattedInstructions.append("-> ").append(instruction).append("\n");
        }

        mealInstructionsTV.setText(formattedInstructions.toString());

        Glide.with(requireContext())
                .load(mealImage)
                .into(mealIMG);

        addToFav.setOnClickListener(v -> onAddFavMealDetailsClicked(meal));

        return view;
    }


    @Override
    public void setData(List<InspirationMeal> inspirationMealList) {

    }

    @Override
    public void showErrMsg(String err) {

    }

    @Override
    public void onAddFavMealDetailsClicked(InspirationMeal inspirationMeal) {
        mealDetailsImp.addToFav(inspirationMeal);
    }

}