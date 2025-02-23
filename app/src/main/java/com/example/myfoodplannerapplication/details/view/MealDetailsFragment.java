package com.example.myfoodplannerapplication.details.view;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.myfoodplannerapplication.R;
import com.example.myfoodplannerapplication.database.MealLocalDataSource;
import com.example.myfoodplannerapplication.details.presenter.MealDetailsImp;
import com.example.myfoodplannerapplication.model.InspirationMeal;
import com.example.myfoodplannerapplication.model.MealRepository;
import com.example.myfoodplannerapplication.model.MealsOfWeek;
import com.example.myfoodplannerapplication.network.MealRemoteDataSource;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.List;

public class MealDetailsFragment extends Fragment implements OnMealDetailsClickListener, MealDetailsView {

    TextView mealNameTV;
    TextView mealInstructionsTV, mealAreaTV;
    ImageView mealIMG;
    ImageView addToFav, addToCalendar;
    MealDetailsImp mealDetailsImp;
    InspirationMeal meal;
    MealsOfWeek mealsOfWeek;
    WebView webView;


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

        meal = MealDetailsFragmentArgs.fromBundle(getArguments()).getMeal();
        MealsOfWeek mealsOfWeek;


        String mealName = meal.getStrMeal();
        String mealInstructions = meal.getStrInstructions();
        String mealImage = meal.getStrMealThumb();
        String mealCountry = meal.getStrArea();
        String mealVideo = meal.getStrYoutube();

        mealNameTV = view.findViewById(R.id.tv_meal_name_in_details);
        mealInstructionsTV = view.findViewById(R.id.tv_meal_instructions_in_details);
        mealIMG = view.findViewById(R.id.img_meal_in_details);
        mealAreaTV = view.findViewById(R.id.tv_area);
        webView = view.findViewById(R.id.wv_video);

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
        addToCalendar.setOnClickListener(v -> {
            showDatePickerDialog();
            Snackbar.make(view, "Done", Snackbar.LENGTH_LONG).show();
        });

        loadYouTubeVideo(meal.getStrYoutube());

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

    @Override
    public void onAddCalendarMealDetailsClicked(MealsOfWeek mealsOfWeek) {
        mealDetailsImp.addToCalendar(mealsOfWeek);
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view, selectedYear, selectedMonth, selectedDay) -> {
            String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;

            Log.d("TAG", "showDatePickerDialog: " + selectedDate);

            mealsOfWeek = new MealsOfWeek(selectedDate, meal.getStrMeal(), meal.getStrMealThumb(), meal.getIdMeal(), meal.getStrCategory(), meal.getStrArea(), meal.getStrInstructions(), meal.getStrYoutube(),
                    meal.getStrIngredient1(), meal.getStrIngredient2(), meal.getStrIngredient3(), meal.getStrIngredient4(), meal.getStrIngredient5(), meal.getStrIngredient6(), meal.getStrIngredient7(), meal.getStrIngredient8(), meal.getStrIngredient9(), meal.getStrIngredient10(), meal.getStrIngredient11(), meal.getStrIngredient12(), meal.getStrIngredient13(), meal.getStrIngredient14(), meal.getStrIngredient15(), meal.getStrIngredient16(), meal.getStrIngredient17(), meal.getStrIngredient18(), meal.getStrIngredient19(), meal.getStrIngredient20());

            mealDetailsImp.addToCalendar(mealsOfWeek);

        }, year, month, day);

        datePickerDialog.show();
    }

    private void loadYouTubeVideo(String url) {
        if (!TextUtils.isEmpty(url)) {
            String videoId = url.split("v=")[1];
            String embedUrl = "https://www.youtube.com/embed/" + videoId;
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setPluginState(WebSettings.PluginState.ON);
            webView.setWebViewClient(new WebViewClient());
            webView.loadUrl(embedUrl);
        }
    }

}