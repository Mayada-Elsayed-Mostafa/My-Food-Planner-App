package com.example.myfoodplannerapplication.details.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myfoodplannerapplication.R;

import java.util.List;

public class IngredientsAndMeasuresAdapter extends RecyclerView.Adapter<IngredientsAndMeasuresAdapter.IngredientViewHolder> {

    private List<String> ingredientsList;
    private List<String> measuresList;

    public IngredientsAndMeasuresAdapter(List<String> ingredientsList, List<String> measuresList) {
        this.ingredientsList = ingredientsList;
        this.measuresList = measuresList;
    }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredients_and_measures_item, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position) {
        String ingredient = ingredientsList.get(position);
        String measure = measuresList.get(position);

        holder.ingredientName.setText(ingredient);
        holder.ingredientMeasure.setText(measure);
    }

    @Override
    public int getItemCount() {
        return ingredientsList.size();
    }

    public static class IngredientViewHolder extends RecyclerView.ViewHolder {

        TextView ingredientName, ingredientMeasure;

        public IngredientViewHolder(View itemView) {
            super(itemView);
            ingredientName = itemView.findViewById(R.id.tv_ingredient_name);
            ingredientMeasure = itemView.findViewById(R.id.tv_ingredient_measure);
        }
    }
}
