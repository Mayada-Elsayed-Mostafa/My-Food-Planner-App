package com.example.myfoodplannerapplication.filtered.mealsFilteredByCategory.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfoodplannerapplication.R;

public class ByCategoryAdapter {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ingredientImg;
        TextView categoryName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ingredientImg = itemView.findViewById(R.id.iv_category_img);
            categoryName = itemView.findViewById(R.id.tv_category_name);

        }
    }
}
