package com.example.myfoodplannerapplication.home.view.meal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myfoodplannerapplication.R;
import com.example.myfoodplannerapplication.home.view.HomeFragment;
import com.example.myfoodplannerapplication.home.view.HomeFragmentDirections;
import com.example.myfoodplannerapplication.model.InspirationMeal;

import java.util.ArrayList;
import java.util.List;

public class MealOfTheDayAdapter extends RecyclerView.Adapter<MealOfTheDayAdapter.ViewHolder> {

    private Context context;
    private List<InspirationMeal> inspirationMeals = new ArrayList<>();
    private OnMealOfTheDayClickListener listener;

    public MealOfTheDayAdapter(List<InspirationMeal> _inspirationMeals, Context context, OnMealOfTheDayClickListener listener) {
        this.inspirationMeals = _inspirationMeals;
        this.context = context;
        this.listener = listener;
    }

    public void setList(List<InspirationMeal> inspirationMeals) {
        this.inspirationMeals = inspirationMeals;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (inspirationMeals != null && !inspirationMeals.isEmpty()) {
            InspirationMeal meal = inspirationMeals.get(position);

            Glide.with(context)
                    .load(meal.getStrMealThumb())
                    .into(holder.mealIV);
            holder.titleTV.setText(meal.getStrMeal());

            holder.mealIV.setOnClickListener(v->{
                listener.onAddMealClicked(meal);
            });

        }

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mealIV;
        TextView titleTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mealIV = itemView.findViewById(R.id.meal_iv);
            titleTV = itemView.findViewById(R.id.title_tv);
        }
    }
}
