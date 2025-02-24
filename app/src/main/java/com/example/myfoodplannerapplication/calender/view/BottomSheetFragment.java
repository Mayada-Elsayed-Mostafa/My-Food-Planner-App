package com.example.myfoodplannerapplication.calender.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfoodplannerapplication.R;
import com.example.myfoodplannerapplication.model.WeekMeals;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class BottomSheetFragment extends BottomSheetDialogFragment {

    private RecyclerView recyclerView;
    private MealAdapter mealAdapter;
    private List<WeekMeals> mealList;

    public static BottomSheetFragment newInstance(List<WeekMeals> mealList) {
        BottomSheetFragment fragment = new BottomSheetFragment();
        Bundle args = new Bundle();
        args.putSerializable("mealList", (ArrayList<WeekMeals>) mealList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_sheet, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        if (getArguments() != null) {
            mealList = (List<WeekMeals>) getArguments().getSerializable("mealList");
        }

        mealAdapter = new MealAdapter(mealList);
        recyclerView.setAdapter(mealAdapter);

        return view;
    }

}
