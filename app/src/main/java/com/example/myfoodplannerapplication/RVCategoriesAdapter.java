package com.example.myfoodplannerapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RVCategoriesAdapter extends RecyclerView.Adapter<RVCategoriesAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<AvailableCategories> categories;

    public RVCategoriesAdapter(Context context, ArrayList<AvailableCategories> categories) {
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.available_category_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        AvailableCategories availableCategories = categories.get(position);

        Glide.with(context)
                .load(availableCategories.getStrCategoryThumb())
                .into(holder.categoryImg);
        holder.categoryName.setText(availableCategories.getStrCategory());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView categoryImg;
        TextView categoryName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryImg = itemView.findViewById(R.id.iv_category_img);
            categoryName = itemView.findViewById(R.id.tv_category_name);

        }
    }
}
