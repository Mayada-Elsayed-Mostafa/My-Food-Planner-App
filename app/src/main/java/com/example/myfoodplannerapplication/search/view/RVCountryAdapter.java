package com.example.myfoodplannerapplication.search.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfoodplannerapplication.R;
import com.example.myfoodplannerapplication.model.Country;

import java.util.List;

public class RVCountryAdapter extends RecyclerView.Adapter<RVCountryAdapter.ViewHolder> {

    private Context context;
    List<Country> countries;
    private OnSearchClickListener listener;

    public RVCountryAdapter(Context _context, List<Country> _countries, OnSearchClickListener _listener) {
        this.context = _context;
        this.countries = _countries;
        this.listener = _listener;
    }

    public void setList(List<Country> countryList) {
        this.countries = countryList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.country_item, parent, false);
        return new RVCountryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Country country = countries.get(position);
        holder.countryName.setText(country.getStrArea());
        holder.countryName.setOnClickListener(v -> {
            listener.onAddSearchClicked(country.getStrArea());
        });
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
        TextView countryName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            countryName = itemView.findViewById(R.id.tv_country_name);

        }
    }

}
