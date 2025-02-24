package com.example.myfoodplannerapplication.home.view.country;

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

public class RVCountriesAdapter extends RecyclerView.Adapter<RVCountriesAdapter.ViewHolder> {


    private Context context;
    private List<Country> countries;

    public RVCountriesAdapter(Context context, List<Country> _countries) {
        this.context = context;
        this.countries = _countries;
    }

    public void setList(List<Country> countryList) {
        this.countries = countryList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.country_item, parent, false);
        return new RVCountriesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Country country = countries.get(position);

        holder.countryName.setText(country.getStrArea());

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
