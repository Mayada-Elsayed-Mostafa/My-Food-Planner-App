package com.example.myfoodplannerapplication.filtered.mealsFilteredBy.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfoodplannerapplication.R;
import com.example.myfoodplannerapplication.model.FilterBy;

import java.util.ArrayList;
import java.util.List;


public class FilterByFragment extends Fragment implements FilterByView {

    FilterByAdapter filterByAdapter;
    RecyclerView byFilterRV;
    List<FilterBy> filters;

    public FilterByFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_by_filter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        byFilterRV = view.findViewById(R.id.rv_by_filter);
        filterByAdapter = new FilterByAdapter(getContext(), new ArrayList<>());
        byFilterRV.setAdapter(filterByAdapter);
        byFilterRV.setLayoutManager(new LinearLayoutManager(getContext()));

    }


    @Override
    public void setFilter(List<FilterBy> filterByList) {
        filters = filterByList;
        if (filterByList != null && !filterByList.isEmpty()) {
            Log.d("Tag", "filters Loaded: " + filterByList.size());
            filterByAdapter.setList(filterByList);
        } else {
            Log.e("Tag", "No filters available to display");
        }
    }
}