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
import com.example.myfoodplannerapplication.model.Category;

import java.util.List;

public class RVCategoriesAdapter extends RecyclerView.Adapter<RVCategoriesAdapter.ViewHolder> {

    private Context context;
    private List<Category> categories;
    private OnSearchClickListener listener;

    public RVCategoriesAdapter(Context context, List<Category> categories, OnSearchClickListener _listener) {
        this.context = context;
        this.categories = categories;
        this.listener = _listener;
    }

    public void setList(List<Category> categoryList) {
        this.categories = categoryList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_meal_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Category category = categories.get(position);

        holder.categoryName.setText(category.getStrCategory());
        Glide.with(holder.itemView.getContext())
                .load(category.getStrCategoryThumb())
                .into(holder.categoryImg);
        holder.categoryImg.setOnClickListener(v -> {
            listener.onAddSearchClicked(category.getStrCategory());
        });
    }

    public void updateList(List<Category> newList) {
        this.categories = newList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView categoryImg;
        TextView categoryName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryImg = itemView.findViewById(R.id.meal_iv);
            categoryName = itemView.findViewById(R.id.title_tv);

        }
    }
}
