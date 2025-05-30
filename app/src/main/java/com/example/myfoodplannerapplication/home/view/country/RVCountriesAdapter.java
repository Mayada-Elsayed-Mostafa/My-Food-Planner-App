package com.example.myfoodplannerapplication.home.view.country;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfoodplannerapplication.databinding.CountryItemBinding;
import com.example.myfoodplannerapplication.model.Country;

import java.util.List;

public class RVCountriesAdapter extends RecyclerView.Adapter<RVCountriesAdapter.ViewHolder> {


    private Context context;
    private List<Country> countries;

    public RVCountriesAdapter(Context context, List<Country> _countries) {
        this.context = context;
        this.countries = _countries;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setList(List<Country> countryList) {
        this.countries = countryList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        CountryItemBinding binding = CountryItemBinding.inflate(inflater, parent, false);
        return new RVCountriesAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Country country = countries.get(position);

        holder.binding.tvCountryName.setText(country.getStrArea());
    }

    @Override
    public int getItemCount() {
        return countries != null ? countries.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CountryItemBinding binding;

        public ViewHolder(@NonNull CountryItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
