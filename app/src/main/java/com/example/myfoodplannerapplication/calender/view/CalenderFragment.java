package com.example.myfoodplannerapplication.calender.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
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

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class CalenderFragment extends Fragment implements OnCalendarClickListener {

    CalendarImp calendarImp;
    CalendarAdapter calendarAdapter;
    RecyclerView recyclerView;

    public CalenderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calender, container, false);

        recyclerView = view.findViewById(R.id.mealPlansRecyclerView);
        calendarAdapter = new CalendarAdapter(new ArrayList<>(), getContext(), this);
        recyclerView.setAdapter(calendarAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        calendarImp = new CalendarImp(MealRepository.getInstance(MealLocalDataSource.getInstance(getContext()), MealRemoteDataSource.getInstance()), this);

        CalendarView calendarView = view.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
            loadMealsForSelectedDate(selectedDate);
        });

        return view;
    }

    private void loadMealsForSelectedDate(String selectedDate) {
        Observable<List<WeekMeals>> mealObservable = calendarImp.getMealsForDate(selectedDate);
        if (mealObservable != null) {
            mealObservable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(mealList -> {
                        if (mealList != null && !mealList.isEmpty()) {
                            Log.d("TAG", "Meals found: " + mealList.size());
                            showBottomSheet(mealList); // هنا نعرض الـ BottomSheet
                        } else {
                            Log.d("TAG", "No meals found for the selected date.");
                        }
                    }, throwable -> {
                        Log.d("TAG", "Error: " + throwable.getMessage());
                    });
        } else {
            Log.e("TAG", "The Observable returned by getMealsForDate() is null");
        }
    }

    // دالة لعرض BottomSheet
    private void showBottomSheet(List<WeekMeals> mealList) {
        BottomSheetFragment bottomSheetFragment = BottomSheetFragment.newInstance(mealList);
        bottomSheetFragment.show(getChildFragmentManager(), bottomSheetFragment.getTag());
    }

    @Override
    public void onMealClicked(WeekMeals meals) {
        calendarImp.delete(meals);
        Toast.makeText(getContext(), "Meal Deleted", Toast.LENGTH_SHORT).show();

        calendarImp.getMealsForDate(meals.getDay()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(updatedMealList -> {
                            calendarAdapter.setList(updatedMealList);
                            calendarAdapter.notifyDataSetChanged();
                        }
                        , throwable -> {
                            Log.d("TAG", "onPlanMealClicked: ");
                        });
    }

    @Override
    public void onImageMealClicked(InspirationMeal meal) {
        CalenderFragmentDirections.ActionCalenderFragmentToMealDetailsFragment action =
                CalenderFragmentDirections.actionCalenderFragmentToMealDetailsFragment(meal);
        Navigation.findNavController(getView()).navigate(action);
    }
}
