package com.example.myfoodplannerapplication.home.view.category;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myfoodplannerapplication.databinding.AvailableCategoryItemBinding;
import com.example.myfoodplannerapplication.model.Category;

import java.util.List;

public class RVCategoriesAdapter extends RecyclerView.Adapter<RVCategoriesAdapter.ViewHolder> {

    private Context context;
    private List<Category> categories;

    public RVCategoriesAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setList(List<Category> categoryList) {
        this.categories = categoryList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        AvailableCategoryItemBinding binding = AvailableCategoryItemBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Category category = categories.get(position);

        holder.binding.tvCategoryName.setText(category.getStrCategory());
        Glide.with(holder.itemView.getContext()).load(category.getStrCategoryThumb()).into(holder.binding.ivCategoryImg);
    }

    @Override
    public int getItemCount() {
        return categories != null ? categories.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        AvailableCategoryItemBinding binding;

        public ViewHolder(@NonNull AvailableCategoryItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
