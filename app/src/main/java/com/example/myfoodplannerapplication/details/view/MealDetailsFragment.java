package com.example.myfoodplannerapplication.details.view;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.myfoodplannerapplication.R;
import com.example.myfoodplannerapplication.database.MealLocalDataSource;
import com.example.myfoodplannerapplication.details.presenter.MealDetailsImp;
import com.example.myfoodplannerapplication.model.InspirationMeal;
import com.example.myfoodplannerapplication.model.MealRepository;
import com.example.myfoodplannerapplication.model.WeekMeals;
import com.example.myfoodplannerapplication.network.MealRemoteDataSource;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.List;

public class MealDetailsFragment extends Fragment implements OnMealDetailsClickListener, MealDetailsView {

    TextView mealNameTV, mealInstructionsTV, mealAreaTV, mealCategoryTV;
    ImageView mealIMG;
    ImageView addToFav, addToCalendar;
    MealDetailsImp mealDetailsImp;
    InspirationMeal meal;
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
        WeekMeals meals;


        String mealName = meal.getStrMeal();
        String mealInstructions = meal.getStrInstructions();
        String mealImage = meal.getStrMealThumb();
        String mealCountry = meal.getStrArea();
        String mealCategory = meal.getStrCategory();

        String mealVideo = meal.getStrYoutube();

        mealNameTV = view.findViewById(R.id.tv_meal_name_in_details);
        mealInstructionsTV = view.findViewById(R.id.tv_meal_instructions_in_details);
        mealIMG = view.findViewById(R.id.img_meal_in_details);
        mealAreaTV = view.findViewById(R.id.tv_area);
        mealCategoryTV = view.findViewById(R.id.tv_category);
        webView = view.findViewById(R.id.wv_video);

        mealNameTV.setText(mealName);
        mealAreaTV.setText(mealCountry);
        mealCategoryTV.setText(mealCategory);

        String[] instructionsList = mealInstructions.split(".\n");

        StringBuilder formattedInstructions = new StringBuilder();
        for (String instruction : instructionsList) {
            formattedInstructions.append("-> ").append(instruction).append("\n");
        }

        mealInstructionsTV.setText(formattedInstructions.toString());

        Glide.with(requireContext())
                .load(mealImage)
                .into(mealIMG);

        addToFav.setOnClickListener(v -> {
            addToFav.setImageResource(R.drawable.bookmark_added);
            onAddFavMealDetailsClicked(meal);
        });


        addToCalendar.setOnClickListener(v2 -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            monthOfYear = monthOfYear + 1;

                            String selectedDate = year + "-" + monthOfYear + "-" + dayOfMonth;

                            WeekMeals weekMeal = new WeekMeals();
                            weekMeal.setId(meal.getIdMeal());
                            weekMeal.setDay(selectedDate);
                            weekMeal.setType(meal.getStrCategory());
                            weekMeal.setMeal(meal);

                            MealLocalDataSource.getInstance(getContext()).insertToWeeklyPlan(weekMeal);
                        }
                    },
                    Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            );

            datePickerDialog.show();
            Snackbar.make(view, "Done", Snackbar.LENGTH_LONG).show();
            addToCalendar.setImageResource(R.drawable.calendar_added);
        });

        loadYouTubeVideo(mealVideo);

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
    public void onAddCalendarMealDetailsClicked(WeekMeals meals) {
        mealDetailsImp.addToCalendar(meals);
    }

    @SuppressLint("SetJavaScriptEnabled")
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