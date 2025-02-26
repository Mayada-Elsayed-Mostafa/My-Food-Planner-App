package com.example.myfoodplannerapplication.calender.view;

import static android.content.Context.MODE_PRIVATE;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfoodplannerapplication.R;
import com.example.myfoodplannerapplication.calender.presenter.CalendarImp;
import com.example.myfoodplannerapplication.database.MealLocalDataSource;
import com.example.myfoodplannerapplication.model.InspirationMeal;
import com.example.myfoodplannerapplication.model.MealRepository;
import com.example.myfoodplannerapplication.model.WeekMeals;
import com.example.myfoodplannerapplication.network.MealRemoteDataSource;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CalenderFragment extends Fragment implements OnCalendarClickListener {

    private boolean isLoggedIn;
    private SharedPreferences preferences;
    private CalendarImp calendarImp;
    private CalendarAdapter calendarAdapter;
    private RecyclerView recyclerView;
    private TextView selectedDayName;
    private ImageView loginFirst;
    FirebaseAuth firebaseAuth;
    String userId;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;

    public CalenderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calender, container, false);

        firebaseAuth = FirebaseAuth.getInstance();

        initializePreferences();
        initializeRecyclerView(view);
        setupCalendarView(view);
        toggleLoginState(view);

        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("Meals");

        FirebaseUser user = firebaseAuth.getCurrentUser();
        userId = user.getUid();

        return view;
    }

    void backupData(WeekMeals meals) {
        if (isLoggedIn) {
            reference.child(userId).child("PlanMeals").child(meals.getId()).setValue(meals)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(getContext(), "Data backed up successfully!", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getContext(), "Failed to back up data!", Toast.LENGTH_SHORT).show();
                        Log.e("BackupError", "Error backing up data: " + e.getMessage());
                    });
        } else {
            Toast.makeText(getContext(), "Please log in to back up your data.", Toast.LENGTH_SHORT).show();
        }
    }

    void restoreData() {
        if (isLoggedIn) {
            reference.child(userId).child("PlanMeals").get()
                    .addOnSuccessListener(dataSnapshot -> {
                        if (dataSnapshot.exists()) {
                            List<WeekMeals> restoredMeals = new ArrayList<>();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                WeekMeals meal = snapshot.getValue(WeekMeals.class);
                                restoredMeals.add(meal);
                            }
                            calendarAdapter.setList(restoredMeals);
                            Toast.makeText(getContext(), "Data restored successfully!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "No backup data found.", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getContext(), "Failed to restore data.", Toast.LENGTH_SHORT).show();
                        Log.e("RestoreError", "Error restoring data: " + e.getMessage());
                    });
        } else {
            Toast.makeText(getContext(), "Please log in to restore your data.", Toast.LENGTH_SHORT).show();
        }
    }

    private void initializePreferences() {
        preferences = getActivity().getSharedPreferences("userData", MODE_PRIVATE);
        isLoggedIn = preferences.getBoolean("isLoggedIn", false);
    }

    private void initializeRecyclerView(View view) {
        loginFirst = view.findViewById(R.id.no_connect_iv);
        selectedDayName = view.findViewById(R.id.selected_day_name);
        recyclerView = view.findViewById(R.id.mealPlansRecyclerView);
        calendarAdapter = new CalendarAdapter(new ArrayList<>(), getContext(), this);
        recyclerView.setAdapter(calendarAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        calendarImp = new CalendarImp(MealRepository.getInstance(MealLocalDataSource.getInstance(getContext()), MealRemoteDataSource.getInstance()), this);
    }

    private void setupCalendarView(View view) {
        CalendarView calendarView = view.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
            loadMealsForSelectedDate(selectedDate);
            displayDayName(year, month, dayOfMonth);
        });
    }

    private void toggleLoginState(View view) {
        if (!isLoggedIn) {
            loginFirst.setVisibility(VISIBLE);
        } else {
            loginFirst.setVisibility(GONE);
        }
    }

    @SuppressLint("CheckResult")
    private void loadMealsForSelectedDate(String selectedDate) {
        Observable<List<WeekMeals>> mealObservable = calendarImp.getMealsForDate(selectedDate);
        mealObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mealList -> calendarAdapter.setList(mealList),
                        throwable -> Log.d("TAG", "Error: " + throwable.getMessage()));
    }

    @SuppressLint("CheckResult")
    @Override
    public void onMealClicked(WeekMeals meals) {
        backupData(meals);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onRemoveMealClicked(WeekMeals meals) {
        calendarImp.delete(meals);
        Toast.makeText(getContext(), "Meal Deleted", Toast.LENGTH_SHORT).show();

        calendarImp.getMealsForDate(meals.getDay())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(updatedMealList -> calendarAdapter.setList(updatedMealList),
                        throwable -> Log.d("TAG", "onPlanMealClicked: " + throwable.getMessage()));
    }

    @Override
    public void onImageMealClicked(InspirationMeal meal) {
        CalenderFragmentDirections.ActionCalenderFragmentToMealDetailsFragment action =
                CalenderFragmentDirections.actionCalenderFragmentToMealDetailsFragment(meal, "");
        Navigation.findNavController(getView()).navigate(action);
    }

    private void displayDayName(int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE", getResources().getConfiguration().locale);
        String dayName = simpleDateFormat.format(calendar.getTime());

        selectedDayName.setVisibility(VISIBLE);
        selectedDayName.setText(dayName);
    }

}
