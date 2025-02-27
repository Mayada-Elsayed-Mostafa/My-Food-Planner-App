package com.example.myfoodplannerapplication.home.view.ingredients;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myfoodplannerapplication.R;
import com.example.myfoodplannerapplication.model.Ingredient;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {

    private Context context;
    List<Ingredient> ingredients;

    public IngredientAdapter(Context context, List<Ingredient> _ingredients) {
        this.context = context;
        this.ingredients = _ingredients;
    }

    public void setList(List<Ingredient> ingredientList) {
        this.ingredients = ingredientList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public IngredientAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.available_category_item, parent, false);
        return new IngredientAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientAdapter.ViewHolder holder, int position) {

        Ingredient ingredient = ingredients.get(position);

        holder.ingredientName.setText(ingredient.getStrIngredient());
        Glide.with(holder.itemView.getContext())
                .load("https://www.themealdb.com/images/ingredients/" + ingredient.getStrIngredient() + ".png")
                .into(holder.ingredientImg);

    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ingredientImg;
        TextView ingredientName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ingredientImg = itemView.findViewById(R.id.iv_category_img);
            ingredientName = itemView.findViewById(R.id.tv_category_name);

        }
    }

}
