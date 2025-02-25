package com.example.myfoodplannerapplication.search.view;

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

public class RVIngredientAdapter extends RecyclerView.Adapter<RVIngredientAdapter.ViewHolder> {

    private Context context;
    List<Ingredient> ingredients;
    private OnSearchClickListener listener;

    public RVIngredientAdapter(Context context, List<Ingredient> _ingredients, OnSearchClickListener _listener) {
        this.context = context;
        this.ingredients = _ingredients;
        this.listener = _listener;
    }

    public void setList(List<Ingredient> ingredientList) {
        this.ingredients = ingredientList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.available_category_item, parent, false);
        return new RVIngredientAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);
        holder.categoryName.setText(ingredient.getStrIngredient());
        Glide.with(holder.itemView.getContext())
                .load("https://www.themealdb.com/images/ingredients/" + ingredient.getStrIngredient() + ".png")
                .into(holder.ingredientImg);
        holder.ingredientImg.setOnClickListener(v -> {
            listener.onAddSearchClicked(ingredient.getStrIngredient());
        });
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ingredientImg;
        TextView categoryName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientImg = itemView.findViewById(R.id.iv_category_img);
            categoryName = itemView.findViewById(R.id.tv_category_name);
        }
    }

    public void updateList(List<Ingredient> newList) {
        this.ingredients = newList;
        notifyDataSetChanged();
    }
}
