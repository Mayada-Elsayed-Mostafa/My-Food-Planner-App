package com.example.myfoodplannerapplication.details.view;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.example.myfoodplannerapplication.model.WeekMeals;
import com.example.myfoodplannerapplication.network.MealRemoteDataSource;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

public class MealDetailsFragment extends Fragment implements OnMealDetailsClickListener {

    private boolean isLoggedIn;
    private SharedPreferences preferences;
    private TextView mealNameTV, mealInstructionsTV, mealAreaTV, mealCategoryTV;
    private ImageView mealIMG, addToFav, addToCalendar;
    private MealDetailsImp mealDetailsImp;
    private InspirationMeal meal;
    private WebView webView;

    public MealDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meal_details, container, false);

        initializeViews(view);
        initializePreferences();
        initializeMealDetails();

        return view;
    }

    private void initializePreferences() {
        preferences = getActivity().getSharedPreferences("userData", MODE_PRIVATE);
        isLoggedIn = preferences.getBoolean("isLoggedIn", false);
    }

    private void initializeMealDetails() {
        mealDetailsImp = new MealDetailsImp(
                MealRepository.getInstance(MealLocalDataSource.getInstance(getContext()), MealRemoteDataSource.getInstance()), this);

        String id = MealDetailsFragmentArgs.fromBundle(getArguments()).getId();

        if (id.isEmpty()) {
            meal = MealDetailsFragmentArgs.fromBundle(getArguments()).getMeal();
            setMealDetails();
        } else {
            mealDetailsImp.getMealById(id);
        }
    }

    private void initializeViews(View view) {
        addToFav = view.findViewById(R.id.add_to_fav);
        addToCalendar = view.findViewById(R.id.add_to_calendar);
        mealNameTV = view.findViewById(R.id.tv_meal_name_in_details);
        mealInstructionsTV = view.findViewById(R.id.tv_meal_instructions_in_details);
        mealIMG = view.findViewById(R.id.img_meal_in_details);
        mealAreaTV = view.findViewById(R.id.tv_area);
        mealCategoryTV = view.findViewById(R.id.tv_category);
        webView = view.findViewById(R.id.wv_video);
    }

    private void setMealDetails() {
        String mealName = meal.getStrMeal();
        String mealInstructions = meal.getStrInstructions();
        String mealImage = meal.getStrMealThumb();
        String mealCountry = meal.getStrArea();
        String mealCategory = meal.getStrCategory();
        String mealVideo = meal.getStrYoutube();

        mealNameTV.setText(mealName);
        mealAreaTV.setText(mealCountry);
        mealCategoryTV.setText(mealCategory);
        mealInstructionsTV.setText(mealInstructions);
        Glide.with(requireContext()).load(mealImage).into(mealIMG);
        loadYouTubeVideo(mealVideo);

        setupAddToFavButton();
        setupAddToCalendarButton();
    }

    private void setupAddToFavButton() {
        addToFav.setOnClickListener(v -> {
            if (isLoggedIn) {
                addToFav.setImageResource(R.drawable.bookmark_added);
                onAddFavMealDetailsClicked(meal);
            }
        });
    }

    private void setupAddToCalendarButton() {
        addToCalendar.setOnClickListener(v -> {
            if (isLoggedIn) {
                showDatePicker();
            }
        });
    }

    private void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                (view, year, monthOfYear, dayOfMonth) -> {
                    monthOfYear = monthOfYear + 1;
                    String selectedDate = year + "-" + monthOfYear + "-" + dayOfMonth;
                    WeekMeals weekMeal = new WeekMeals();
                    weekMeal.setId(meal.getIdMeal());
                    weekMeal.setDay(selectedDate);
                    weekMeal.setType(meal.getStrCategory());
                    weekMeal.setMeal(meal);

                    MealLocalDataSource.getInstance(getContext()).insertToWeeklyPlan(weekMeal);
                    Snackbar.make(getView(), "Done", Snackbar.LENGTH_LONG).show();
                    addToCalendar.setImageResource(R.drawable.calendar_added);
                },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
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

    @Override
    public void onAddFavMealDetailsClicked(InspirationMeal inspirationMeal) {
        mealDetailsImp.addToFav(inspirationMeal);
    }

    @Override
    public void onAddCalendarMealDetailsClicked(WeekMeals meals) {
        mealDetailsImp.addToCalendar(meals);
    }

    public void displayMeal(InspirationMeal _meal) {
        this.meal = _meal;
        setMealDetails();
    }
}
