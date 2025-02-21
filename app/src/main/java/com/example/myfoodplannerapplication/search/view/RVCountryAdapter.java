package com.example.myfoodplannerapplication.search.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfoodplannerapplication.R;
import com.example.myfoodplannerapplication.model.Category;
import com.example.myfoodplannerapplication.model.Country;

import java.util.List;

public class RVCountryAdapter extends RecyclerView.Adapter<RVCountryAdapter.ViewHolder> {

    private Context context;
    List<Country> countries;

    public RVCountryAdapter(Context _context, List<Country> _countries) {
        this.context = _context;
        this.countries = _countries;
    }

    public void setList(List<Country> countryList) {
        this.countries = countryList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.available_category_item, parent, false);
        return new RVCountryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Country country = countries.get(position);

        holder.categoryName.setText(country.getStrArea());
//        Glide.with(holder.itemView.getContext())
//                .load(country.getStrCategoryThumb())
//                .into(holder.categoryImg);
//
//        Log.d("RVCategoriesAdapter", "Loading image for category: " + category.getStrCategoryThumb());

    }

    public void updateList(List<Country> newList) {
        this.countries = newList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        //ImageView countryIMg;
        TextView categoryName;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            //countryIMg = itemView.findViewById(R.id.iv_category_img);
            categoryName = itemView.findViewById(R.id.tv_category_name);

        }
    }

}
