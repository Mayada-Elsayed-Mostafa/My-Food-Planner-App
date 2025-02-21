package com.example.myfoodplannerapplication.filtered.mealsFilteredBy.view;

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
import com.example.myfoodplannerapplication.model.FilterBy;

import java.util.List;

public class FilterByAdapter extends RecyclerView.Adapter<FilterByAdapter.ViewHolder> {
    private Context context;
    private List<FilterBy> filterByList;

    public FilterByAdapter(Context context, List<FilterBy> _filterByList) {
        this.context = context;
        this.filterByList = _filterByList;
    }

    public void setList(List<FilterBy> _filterByList) {
        this.filterByList = _filterByList;
        notifyDataSetChanged();
    }

    public void updateList(List<FilterBy> newList) {
        this.filterByList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.available_category_item, parent, false);
        return new FilterByAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        FilterBy filter = filterByList.get(position);

        holder.categoryName.setText(filter.getStrMeal());
        Glide.with(holder.itemView.getContext())
                .load(filter.getStrMealThumb())
                .into(holder.categoryImg);


    }

    @Override
    public int getItemCount() {
        return filterByList.size();
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
