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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myfoodplannerapplication.R;
import com.example.myfoodplannerapplication.database.MealLocalDataSource;
import com.example.myfoodplannerapplication.details.presenter.MealDetailsImp;
import com.example.myfoodplannerapplication.model.InspirationMeal;
import com.example.myfoodplannerapplication.model.MealRepository;
import com.example.myfoodplannerapplication.model.WeekMeals;
import com.example.myfoodplannerapplication.network.MealRemoteDataSource;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MealDetailsFragment extends Fragment implements OnMealDetailsClickListener {

    private boolean isLoggedIn;
    private SharedPreferences preferences;
    private TextView mealNameTV, mealInstructionsTV, mealAreaTV, mealCategoryTV;
    private ImageView mealIMG, addToFav, addToCalendar;
    private MealDetailsImp mealDetailsImp;
    private InspirationMeal meal;
    private WebView webView;
    private RecyclerView rvIngredientsAndMeasures;
    private IngredientsAndMeasuresAdapter ingredientsAndMeasuresAdapter;
    private List<InspirationMeal> mealsList;
    List<String> ingredientsList = new ArrayList<>();
    List<String> MeasuresList = new ArrayList<>();


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

        addToFav = view.findViewById(R.id.add_to_fav);
        addToCalendar = view.findViewById(R.id.add_to_calendar);
        mealNameTV = view.findViewById(R.id.tv_meal_name_in_details);
        mealInstructionsTV = view.findViewById(R.id.tv_meal_instructions_in_details);
        mealIMG = view.findViewById(R.id.img_meal_in_details);
        mealAreaTV = view.findViewById(R.id.tv_area);
        mealCategoryTV = view.findViewById(R.id.tv_category);
        webView = view.findViewById(R.id.wv_video);

        preferences = getActivity().getSharedPreferences("userData", MODE_PRIVATE);
        isLoggedIn = preferences.getBoolean("isLoggedIn", false);

        initializeMealDetails();

        rvIngredientsAndMeasures = view.findViewById(R.id.rv_ingredients_list);
        rvIngredientsAndMeasures.setLayoutManager(new LinearLayoutManager(getContext()));


        ingredientsAndMeasuresAdapter = new IngredientsAndMeasuresAdapter(ingredientsList, MeasuresList);
        rvIngredientsAndMeasures.setAdapter(ingredientsAndMeasuresAdapter);

        return view;
    }

    private void initializeMealDetails() {
        mealDetailsImp = new MealDetailsImp(
                MealRepository.getInstance(MealLocalDataSource.getInstance(getContext()), MealRemoteDataSource.getInstance()), this);

        String id = MealDetailsFragmentArgs.fromBundle(getArguments()).getId();

        if (id.isEmpty()) {
            meal = MealDetailsFragmentArgs.fromBundle(getArguments()).getMeal();
            String mealName = meal.getStrMeal();
            String mealInstructions = meal.getStrInstructions();
            String mealImage = meal.getStrMealThumb();
            String mealCountry = meal.getStrArea();
            String mealCategory = meal.getStrCategory();
            String mealVideo = meal.getStrYoutube();

            String[] ingredients = {
                    meal.getStrIngredient1(),
                    meal.getStrIngredient2(),
                    meal.getStrIngredient3(),
                    meal.getStrIngredient4(),
                    meal.getStrIngredient5(),
                    meal.getStrIngredient6(),
                    meal.getStrIngredient7(),
                    meal.getStrIngredient8(),
                    meal.getStrIngredient9(),
                    meal.getStrIngredient10(),
                    meal.getStrIngredient11(),
                    meal.getStrIngredient12(),
                    meal.getStrIngredient13(),
                    meal.getStrIngredient14(),
                    meal.getStrIngredient15(),
                    meal.getStrIngredient16(),
                    meal.getStrIngredient17(),
                    meal.getStrIngredient18(),
                    meal.getStrIngredient19(),
                    meal.getStrIngredient20()
            };

            for (String ingredient : ingredients) {
                if (ingredient != null && !ingredient.isEmpty()) {
                    ingredientsList.add(ingredient);
                }
            }

            String[] measures = {
                    meal.getStrMeasure1(),
                    meal.getStrMeasure2(),
                    meal.getStrMeasure3(),
                    meal.getStrMeasure4(),
                    meal.getStrMeasure5(),
                    meal.getStrMeasure6(),
                    meal.getStrMeasure7(),
                    meal.getStrMeasure8(),
                    meal.getStrMeasure9(),
                    meal.getStrMeasure10(),
                    meal.getStrMeasure11(),
                    meal.getStrMeasure12(),
                    meal.getStrMeasure13(),
                    meal.getStrMeasure14(),
                    meal.getStrMeasure15(),
                    meal.getStrMeasure16(),
                    meal.getStrMeasure17(),
                    meal.getStrMeasure18(),
                    meal.getStrMeasure19(),
                    meal.getStrMeasure20()
            };

            for (String measure : measures) {
                if (measure != null && !measure.isEmpty()) {
                    MeasuresList.add(measure);
                }
            }

            mealNameTV.setText(mealName);
            mealAreaTV.setText(mealCountry);
            mealCategoryTV.setText(mealCategory);
            mealInstructionsTV.setText(mealInstructions);
            Glide.with(requireContext()).load(mealImage).into(mealIMG);
            loadYouTubeVideo(mealVideo);

            setupAddToFavButton();
            setupAddToCalendarButton();
        } else {
            mealDetailsImp.getMealById(id);
        }
    }

    private void setupAddToFavButton() {
        addToFav.setOnClickListener(v -> {
            if (isLoggedIn) {
                addToFav.setImageResource(R.drawable.added_to_fav);
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
                    addToCalendar.setImageResource(R.drawable.added_to_calendar);
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
}
