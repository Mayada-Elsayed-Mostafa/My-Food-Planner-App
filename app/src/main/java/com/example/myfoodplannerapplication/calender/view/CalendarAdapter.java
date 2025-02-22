package com.example.myfoodplannerapplication.calender.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myfoodplannerapplication.R;
import com.example.myfoodplannerapplication.model.MealsOfWeek;

import java.util.ArrayList;
import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.ViewHolder> {


    private Context context;
    private List<MealsOfWeek> mealsOfWeeks = new ArrayList<>();
    private OnCalendarClickListener onCalendarClickListener;

    public CalendarAdapter(List<MealsOfWeek> _mealsOfWeeks, Context context, OnCalendarClickListener _onCalendarClickListener) {
        this.mealsOfWeeks = _mealsOfWeeks;
        this.context = context;
        this.onCalendarClickListener = _onCalendarClickListener;
    }

    public void setList(List<MealsOfWeek> _mealsOfWeeks) {
        this.mealsOfWeeks = _mealsOfWeeks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fav_meal_item, parent, false);
        return new CalendarAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (mealsOfWeeks != null && !mealsOfWeeks.isEmpty()) {
            MealsOfWeek meal = mealsOfWeeks.get(position);
            Glide.with(context)
                    .load(meal.getStrMealThumb())
                    .into(holder.mealIV);
            holder.titleTV.setText(meal.getStrMeal());

            holder.remove.setOnClickListener(view -> {
                onCalendarClickListener.onMealClicked(meal);
            });
        }

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mealIV;
        TextView titleTV;
        Button remove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mealIV = itemView.findViewById(R.id.fav_meal_iv);
            titleTV = itemView.findViewById(R.id.fav_title_tv);
            remove = itemView.findViewById(R.id.btn_remove);
        }
    }
}