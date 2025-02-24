package com.example.myfoodplannerapplication.calender.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myfoodplannerapplication.R;
import com.example.myfoodplannerapplication.model.InspirationMeal;
import com.example.myfoodplannerapplication.model.WeekMeals;

import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder> {

    private List<WeekMeals> mealList;
    private OnCalendarClickListener onCalendarClickListener;
    private Context context;

    public MealAdapter(List<WeekMeals> mealList) {
        this.mealList = mealList;
    }

    @Override
    public MealViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_meal_item, parent, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MealViewHolder holder, int position) {
        WeekMeals meal = mealList.get(position);
        Glide.with(context)
                .load(meal.getMeal().getStrMealThumb())
                .into(holder.mealImage);
        holder.mealName.setText(meal.getMeal().getStrMeal());
        holder.mealImage.setOnClickListener(view -> {
            onCalendarClickListener.onImageMealClicked(meal.getMeal());
        });

    }

    @Override
    public int getItemCount() {
        return mealList.size();
    }

    public static class MealViewHolder extends RecyclerView.ViewHolder {
        TextView mealName;
        ImageView mealImage;

        public MealViewHolder(View itemView) {
            super(itemView);
            mealName = itemView.findViewById(R.id.fav_title_tv);
            mealImage = itemView.findViewById(R.id.fav_meal_iv);
        }
    }
}
