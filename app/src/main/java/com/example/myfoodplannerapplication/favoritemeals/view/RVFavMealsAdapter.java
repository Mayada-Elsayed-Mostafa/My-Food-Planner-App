package com.example.myfoodplannerapplication.favoritemeals.view;

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
import com.example.myfoodplannerapplication.model.InspirationMeal;

import java.util.ArrayList;
import java.util.List;

public class RVFavMealsAdapter extends RecyclerView.Adapter<RVFavMealsAdapter.ViewHolder> {

    private Context context;
    private List<InspirationMeal> favMeals = new ArrayList<>();
    private OnFavMealClickListener listener;

    public RVFavMealsAdapter(List<InspirationMeal> _favMeals, Context context, OnFavMealClickListener listener) {
        this.favMeals = _favMeals;
        this.context = context;
        this.listener = listener;
    }

    public void setList(List<InspirationMeal> _favMeals) {
        this.favMeals = _favMeals;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fav_meal_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (favMeals != null && !favMeals.isEmpty()) {
            InspirationMeal meal = favMeals.get(position);
            Glide.with(context)
                    .load(meal.getStrMealThumb())
                    .into(holder.mealIV);
            holder.titleTV.setText(meal.getStrMeal());
            holder.mealIV.setOnClickListener(v -> {
                InspirationMeal selectedMeal = meal;
                FavoriteFragmentDirections.ActionFavoriteFragmentToMealDetailsFragment action =
                        FavoriteFragmentDirections.actionFavoriteFragmentToMealDetailsFragment(selectedMeal);
                Navigation.findNavController(v).navigate(action);


            });
            holder.remove.setOnClickListener(view -> {
                listener.onFavMealClicked(meal);
            });


        }
    }

    @Override
    public int getItemCount() {
        return favMeals.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mealIV;
        TextView titleTV;
        ImageView remove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mealIV = itemView.findViewById(R.id.fav_meal_iv);
            titleTV = itemView.findViewById(R.id.fav_title_tv);
            remove = itemView.findViewById(R.id.btn_remove);
        }
    }
}
